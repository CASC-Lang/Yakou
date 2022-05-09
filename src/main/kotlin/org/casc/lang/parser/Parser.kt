package org.casc.lang.parser

import org.casc.lang.ast.*
import org.casc.lang.ast.Function
import org.casc.lang.compilation.AbstractPreference
import org.casc.lang.compilation.Error
import org.casc.lang.compilation.Report
import org.casc.lang.compilation.Warning
import org.casc.lang.table.HasFlag
import org.casc.lang.table.Reference
import org.casc.lang.utils.MutableObjectSet
import org.casc.lang.utils.Tuple4
import org.objectweb.asm.Opcodes
import java.io.File as JFile

class Parser(private val preference: AbstractPreference) {
    private var pos: Int = 0
    private var reports = mutableListOf<Report>()
    private lateinit var tokens: List<Token>

    fun parse(path: String, relativeFilePath: String, tokens: List<Token>): Pair<List<Report>, File?> {
        pos = 0
        this.tokens = tokens
        val file = parseFile(path, relativeFilePath)

        return reports to file
    }

    // ================================
    // PRIVATE UTILITY FUNCTIONS
    // ================================

    private fun hasNext(): Boolean = tokens.size > pos

    private fun assertUntil(type: TokenType): Token? {
        while (hasNext()) {
            val token = assert(type)

            if (token != null) return token
        }

        return null
    }

    private fun assert(type: TokenType): Token? = when {
        !hasNext() -> null
        tokens[pos].type == type -> tokens[pos++]
        else -> {
            reports.reportUnexpectedToken(type, tokens[pos++])
            null
        }
    }

    private fun assertUntil(predicate: (Token) -> Boolean): Token? {
        while (hasNext()) {
            val token = assert(predicate)

            if (token != null) return token
        }

        return null
    }

    private fun assert(predicate: (Token) -> Boolean): Token? = when {
        !hasNext() -> null
        predicate(tokens[pos]) -> tokens[pos++]
        else -> {
            reports += Error(
                tokens[pos].pos, "Unexpected token ${tokens[pos++].type}, expected predicate $predicate"
            )
            null
        }
    }

    private fun peek(offset: Int = 0): Token? = when {
        tokens.isEmpty() -> null
        pos + offset >= tokens.size -> null
        else -> tokens[pos + offset]
    }

    private inline fun peekIf(predicate: (Token) -> Boolean, offset: Int = 0): Boolean = peek(offset)?.let {
        predicate(it)
    } ?: false

    private fun peekIf(type: TokenType, offset: Int = 0): Boolean = peek(offset)?.let {
        it.type == type
    } ?: false

    /**
     * peekMultiple is used to determine whether a set of tokens matches specific syntax pattern.
     */
    private fun peekMultiple(vararg types: TokenType): Boolean {
        for (i in types.indices) if (peek(i)?.type != types[i]) return false

        return true
    }

    private fun last(): Token? = peek(-1)

    private fun next(): Token? = when {
        !hasNext() -> null
        else -> tokens[pos++]
    }

    /**
     * Get next token if there are more than 1 tokens remaining, otherwise return last token if it exists
     */
    private fun nextOrLast(): Token? = if (hasNext()) next() else tokens.lastOrNull()

    private fun consume() {
        if (hasNext()) pos++
    }

    // ================================
    // PARSING FUNCTIONS
    // ================================

    private fun parseFile(path: String, relativeFilePath: String): File? {
        val fileName = JFile(path).nameWithoutExtension

        // Parse optional package declaration
        val packageReference = if (peekIf(Token::isPackageKeyword)) {
            // package Module
            consume()
            parseTypeSymbol()
        } else null

        // Parse usages
        val usages = mutableListOf<Reference>()
        val typeInstances = mutableMapOf<Reference, TypeInstance>()
        val majorImpls = mutableMapOf<Reference, Impl>()
        val traitImpls = mutableMapOf<Reference, List<TraitImpl>>()

        while (hasNext()) {
            if (peekIf(Token::isUseKeyword)) {
                consume()
                usages += parseUsage()
                continue
            }

            val (accessor, abstr, _, mutable) = parseModifiers(ovrdKeyword = false) { it.isClassKeyword() || it.isTraitKeyword() || it.isImplKeyword() }

            if (peekIf(Token::isClassKeyword)) {
                // Class declaration
                val classKeyword = next()!!
                val classReference = parseTypeSymbol()
                val fields = if (peekIf(TokenType.OpenBrace)) {
                    consume() // open brace
                    val fields = parseFields(classReference)
                    assertUntil(TokenType.CloseBrace)
                    fields
                } else listOf()


                // Missing class reference, most likely caused by token missing
                if (classReference.fullQualifiedPath.isEmpty()) continue

                val classInstance =
                    ClassInstance(packageReference, accessor, abstr, mutable, classKeyword, classReference, fields)

                if (typeInstances.containsKey(classReference)) {
                    // Class declaration duplication
                    reports += Error(
                        classReference.pos,
                        "Duplicated class declaration for class ${classReference.asCASCStyle()}",
                        "Consider remove this class declaration"
                    )
                } else typeInstances[classReference] = classInstance
            } else if (peekIf(Token::isTraitKeyword)) {
                // Trait declaration
                if (abstr != null) {
                    // Trait with `abstr` keyword
                    reports += Error(
                        abstr.pos,
                        "Cannot declare trait instance with `abstr` keyword, trait is implicitly abstract",
                        "Remove this `abstr` keyword"
                    )
                }

                if (mutable != null) {
                    // Trait with `mut` keyword
                    reports += Error(
                        mutable.pos,
                        "Cannot declare trait instance with `mut` keyword, trait is implicitly mutable",
                        "Remove this `mut` keyword"
                    )
                }

                val traitKeyword = next()!!
                val traitReference = parseTypeSymbol()
                val fields = if (peekIf(TokenType.OpenBrace)) {
                    consume() // open brace
                    val fields = parseFields(traitReference)
                    assertUntil(TokenType.CloseBrace)
                    fields
                } else listOf()

                for (field in fields) {
                    if (field.compKeyword == null) {
                        // non-companion field in trait
                        reports += Error(
                            field.name?.pos,
                            "Cannot declare non-companion field ${field.name?.literal ?: "<Unknown>"} in trait",
                            "Declare this field in companion block"
                        )
                    }
                }

                // Missing class reference, most likely caused by token missing
                if (traitReference.fullQualifiedPath.isEmpty()) continue

                val traitInstance =
                    TraitInstance(packageReference, accessor, traitKeyword, traitReference, fields)

                if (typeInstances.containsKey(traitReference)) {
                    // Trait declaration duplication
                    reports += Error(
                        traitReference.pos,
                        "Duplicated trait declaration for class ${traitReference.asCASCStyle()}",
                        "Consider remove this trait declaration"
                    )
                } else typeInstances[traitReference] = traitInstance
            } else if (peekIf(Token::isImplKeyword)) {
                val implKeyword = next()!!
                val ownerReference = parseTypeSymbol()

                if (peekIf(Token::isForKeyword)) {
                    // TODO
                } else {
                    // Common implementation

                    val parentClassReference = if (peekIf(TokenType.Colon)) {
                        consume() // colon
                        parseTypeSymbol()
                    } else null

                    var functions: List<Function>? = null
                    var constructors: List<Constructor>? = null
                    var companionBlock: List<Statement>? = null

                    if (peekIf(TokenType.OpenBrace)) {
                        consume() // open brace

                        val (fns, ctors, compBlocks) = parseFunctions(ownerReference, parentClassReference)
                        functions = fns
                        constructors = ctors
                        companionBlock = compBlocks

                        assertUntil(TokenType.CloseBrace)
                    }

                    // Missing class reference, most likely caused by token missing
                    if (ownerReference.fullQualifiedPath.isEmpty()) continue

                    val impl = Impl(
                        implKeyword,
                        parentClassReference,
                        companionBlock ?: listOf(),
                        constructors ?: listOf(),
                        functions ?: listOf()
                    )

                    if (!typeInstances.containsKey(ownerReference)) {
                        // Unknown type implementation
                        reports += Error(
                            ownerReference.pos, "Unknown type implementation for class ${ownerReference.asCASCStyle()}"
                        )
                    } else if (majorImpls.containsKey(ownerReference)) {
                        // Implementation duplication
                        reports += Error(
                            ownerReference.pos,
                            "Duplicated type implementation for class ${ownerReference.asCASCStyle()}",
                            "Consider remove this type implementation"
                        )
                    } else majorImpls[ownerReference] = impl
                }
            }
        }

        // Bind major type instance
        var typeInstance: TypeInstance? = null

        if (typeInstances.isEmpty()) {
            // Type declaration missing
            // CASC is unable to determine which type instance to be generated for single class file
            reports += Error(
                "Type declaration missing, unable to determine type instance $fileName"
            )
        } else if (typeInstances.keys.find { it.className == fileName } == null) {
            // No type instance's name is same as file name
            reports += Error(
                "Type instance $fileName must be declared."
            )
        } else if (typeInstances.keys.filter { it.fullQualifiedPath.split(".").size == 1 }.size > 1) {
            // More than one major type instance
            reports += Error(
                "Cannot have multiple major type instances, only type instance $fileName is allowed"
            )
        } else typeInstance = typeInstances[Reference(fileName)]

        // Bind implementations to type instances
        for ((typeInstanceReference, typeInstanceEntry) in typeInstances) {
            majorImpls[typeInstanceReference]?.let {
                when (typeInstanceEntry) {
                    is ClassInstance -> {
                        for (function in it.functions) {
                            if (function.statements == null && (typeInstanceEntry.abstrToken == null || function.abstrKeyword == null)) {
                                // Function body missing while function is not declared with `abstr` keyword
                                reports += Error(
                                    function.name?.pos,
                                    "Function body must be implemented when both class instance and function didn't declared with `abstr` keyword",
                                    "Add function body"
                                )
                            }

                            if (function.selfKeyword == null && function.statements == null) {
                                // Companion function body missing
                                reports += Error(
                                    function.name?.pos,
                                    "Companion function must have function body"
                                )
                            }
                        }
                    }
                    is TraitInstance -> {
                        if (it.parentClassReference != null) {
                            // Illegal inheritance for trait instance's implementation
                            reports += Error(
                                it.parentClassReference.pos,
                                "Trait cannot inherit type instance by major implementation",
                                "Replace `:` (colon) with `for` keyword"
                            )
                        }

                        if (it.constructors.isNotEmpty()) {
                            // Illegal constructor declaration for trait instance's implementation
                            for (constructor in it.constructors) {
                                reports += Error(
                                    constructor.newKeyword?.pos,
                                    "Trait cannot have constructors",
                                    "Remove this constructor declaration"
                                )
                            }
                        }

                        for (function in it.functions) {
                            if (function.selfKeyword == null && function.statements == null) {
                                // Companion function body missing
                                reports += Error(
                                    function.name?.pos,
                                    "Companion function must have function body"
                                )
                            }
                        }
                    }
                }

                typeInstanceEntry.impl = it
            }

            traitImpls[typeInstanceReference]?.let {
                typeInstanceEntry.traitImpls = it
            }
        }

        // Bind member type instances to major type instance
        // TODO

        return typeInstance?.let { File(path, relativeFilePath, usages, it) }
    }

    /**
     * Used to parse type symbol (excluding array type symbol), not designed to be parsed with `use` syntax.
     */
    private fun parseTypeSymbol(): Reference {
        var currentIdentifier = assertUntil(TokenType.Identifier)
        val tokens = mutableListOf<Token?>()
        var nameBuilder = currentIdentifier?.literal

        tokens += currentIdentifier

        while (hasNext()) {
            if (!peekMultiple(TokenType.DoubleColon, TokenType.Identifier)) break

            consume()
            currentIdentifier = assertUntil(TokenType.Identifier)

            nameBuilder += ".${currentIdentifier?.literal}"
            tokens += currentIdentifier
        }

        return Reference(nameBuilder ?: "", *tokens.toTypedArray())
    }

    /**
     * Used to parse type symbol with potential array suffix ([]), not designed to be parsed with `use` syntax
     */
    private fun parseComplexTypeSymbol(): Reference {
        var arrayDimensionCounter = 0

        while (peekIf(TokenType.OpenBracket)) {
            consume()
            arrayDimensionCounter++
        }

        val arrayDimension = arrayDimensionCounter
        val baseTypeSymbol = parseTypeSymbol()

        while (arrayDimensionCounter != 0) {
            assertUntil(TokenType.CloseBracket) ?: break // out of token will immediately break out of loop

            arrayDimensionCounter--
        }

        baseTypeSymbol.appendArrayDimension(arrayDimension)

        return baseTypeSymbol
    }

    private fun parseUsage(prependReference: Reference? = null): List<Reference> {
        val references = mutableListOf<Reference>()
        val reference = parseTypeSymbol()

        if (peekMultiple(TokenType.DoubleColon, TokenType.OpenBrace)) {
            // use Module::{ SubModuleA::ClassA, SubModuleB::ClassB }
            consume()
            consume()

            while (hasNext()) {
                if (peekIf(TokenType.CloseBrace)) break

                references += parseUsage(reference)

                if (peekIf(TokenType.Comma)) {
                    consume()
                    continue
                } else break
            }

            assertUntil(TokenType.CloseBrace)
        } else if (peekMultiple(TokenType.DoubleColon, TokenType.Star)) {
            // Wildcard usage
            // use Module::*
            consume()
            val star = next()

            if (prependReference != null) {
                prependReference.append(reference.fullQualifiedPath)
                prependReference.className = "*"

                prependReference.pos?.extendSelf(star?.pos)

                references += prependReference
            } else {
                reference.className = "*"

                reference.pos?.extendSelf(star?.pos)

                references += reference
            }
        } else if (peekIf(TokenType.Identifier) && peekIf(Token::isAsKeyword)) {
            // use Module::Class as Cls
            consume()
            val aliasReference = assertUntil(TokenType.Identifier)

            if (prependReference != null) {
                prependReference.append(reference.fullQualifiedPath)
                prependReference.className = aliasReference?.literal ?: ""

                prependReference.pos?.extendSelf(aliasReference?.pos)

                references += prependReference
            } else {
                reference.className = aliasReference?.literal ?: ""

                reference.pos?.extendSelf(aliasReference?.pos)

                references += reference
            }
        } else references += reference

        return references
    }

    /**
     * parseModifiers follows the following modifier sequence:
     * (`pub`#1 / `prot` / `intl` / `priv`)? (`ovrd`)? (`abstr`)? (`mut`)?
     *
     * Notice that `abstr` and `mut` keywords are conflicted, when conflicted, sequence error will be muted, instead,
     * generates a conflicted error
     *
     * #1: Will generate a warning by default.
     */
    private fun parseModifiers(
        accessModifier: Boolean = true,
        ovrdKeyword: Boolean = true,
        abstrKeyword: Boolean = true,
        mutableKeyword: Boolean = true,
        forbidPubAccessor: Boolean = true,
        terminator: (Token) -> Boolean
    ): Tuple4<Token?, Token?, Token?, Token?> {
        var accessor: Token? = null
        var abstr: Token? = null
        var ovrd: Token? = null
        var mutable: Token? = null

        while (hasNext() && !terminator(peek()!!)) {
            val token = next()!!

            if (token.isAccessorKeyword()) {
                if (accessModifier) {
                    if (accessor != null) {
                        reports += Error(
                            accessor.pos,
                            "Duplicate access modifier `${accessor.literal}`",
                            "Encountered first one here"
                        )
                        reports += Error(
                            token.pos, "Duplicate access modifier `${token.literal}`", "Duplicate here"
                        )
                    }

                    if (abstrKeyword && abstr != null) {
                        reports += Error(
                            abstr.pos, "Cannot declare access modifier after `abstr` keyword", "`abstr` keyword here"
                        )
                    }
                    if (ovrdKeyword && ovrd != null) {
                        reports += Error(
                            ovrd.pos, "Cannot declare access modifier after `ovrd` keyword", "`ovrd` keyword here"
                        )
                    }
                    if (mutableKeyword && mutable != null) {
                        reports += Error(
                            mutable.pos, "Cannot declare access modifier after `mut` keyword", "`mut` keyword here"
                        )
                    }
                    if (forbidPubAccessor && token.literal == "pub") {
                        reports += Error(
                            token.pos,
                            "Redundant access modifier `pub`, `pub` is declared under the hood by compiler",
                            "Remove this access modifier `pub`"
                        )
                    }

                    accessor = token
                } else {
                    reports += Error(
                        token.pos,
                        "Cannot declare access modifier in current context",
                        "Remove this access modifier `${token.literal}`"
                    )
                }
            } else if (token.isAbstrKeyword()) {
                if (abstrKeyword) {
                    if (abstr != null) {
                        reports += Error(
                            abstr.pos, "Duplicate `abstr` keyword", "Encountered first one here"
                        )
                        reports += Error(
                            token.pos, "Duplicate `abstr` keyword", "Duplicate here"
                        )
                    }

                    if (mutableKeyword && mutable != null) {
                        // Conflicted modifiers
                        reports += Error(
                            mutable.pos,
                            "Cannot declare `abstr` keyword while `mut` keyword was declared",
                            "`mut` keyword here"
                        )
                    }

                    abstr = token
                } else {
                    reports += Error(
                        token.pos,
                        "Cannot declare `abstr` keyword in current context",
                        "Remove this `abstr` keyword"
                    )
                }
            } else if (token.isOvrdKeyword()) {
                if (ovrdKeyword) {
                    if (ovrd != null) {
                        reports += Error(
                            ovrd.pos, "Duplicate `ovrd` keyword", "Encountered first one here"
                        )
                        reports += Error(
                            token.pos, "Duplicate `ovrd` keyword", "Duplicate here"
                        )
                    }

                    if (abstrKeyword && abstr != null) {
                        reports += Error(
                            abstr.pos, "Cannot declare `ovrd` keyword after `abstr` keyword", "`abstr` keyword here"
                        )
                    }
                    if (mutableKeyword && mutable != null) {
                        reports += Error(
                            mutable.pos, "Cannot declare `ovrd` keyword after `mut` keyword", "`mut` keyword here"
                        )
                    }

                    ovrd = token
                } else {
                    reports += Error(
                        token.pos, "Cannot declare `ovrd` keyword in current context", "Remove this `ovrd` keyword"
                    )
                }
            } else if (token.isMutKeyword()) {
                if (mutableKeyword) {
                    if (mutable != null) {
                        reports += Error(
                            mutable.pos, "Duplicate `mut` keyword", "Encountered first one here"
                        )
                        reports += Error(
                            token.pos, "Duplicate `mut` keyword", "Duplicate here"
                        )
                    }

                    if (abstrKeyword && abstr != null) {
                        // Conflicted modifiers
                        reports += Error(
                            abstr.pos,
                            "Cannot declare `mut` keyword while `abstr` keyword was declared",
                            "`abstr` keyword here"
                        )
                    }

                    mutable = token
                } else {
                    reports += Error(
                        token.pos, "Cannot declare `mut` keyword in current context", "Remove this `mut` keyword"
                    )
                }
            } else {
                var builder = if (accessModifier) "access modifiers (`pub`, `prot`, `intl`, `priv`) " else ""
                builder += if (builder.isNotEmpty() && ovrdKeyword) "or `ovrd` keyword" else if (ovrdKeyword) "`ovrd` keyword" else ""
                builder += if (builder.isNotEmpty() && mutableKeyword) "or `mut` keyword" else if (mutableKeyword) "`mut` keyword" else ""

                reports += Error(
                    token.pos, "Unexpected token ${token.type}, expected $builder"
                )
            }
        }

        return Tuple4(accessor, abstr, ovrd, mutable)
    }

    private fun parseFields(
        classReference: Reference?, compKeyword: Token? = null
    ): List<Field> {
        var accessorToken: Token? = null
        var abstrToken: Token? = null
        var mutKeyword: Token? = null
        var compScopeDeclared = false
        val usedFlags = mutableSetOf(Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL) // Default flag is pub (final)
        val fields = object : MutableObjectSet<Field>() {
            override fun isDuplicate(a: Field, b: Field): Boolean = a.name?.literal == b.name?.literal
        }

        while (hasNext()) {
            if (peekIf(TokenType.CloseBrace)) break

            if (peekIf(Token::isCompKeyword)) {
                // Companion scope
                // comp { ... }
                val comp = next()!!

                if (compScopeDeclared) {
                    reports += Warning(
                        comp.pos, "Companion declaration scope has been declared once"
                    )
                } else compScopeDeclared = true

                if (compKeyword != null) {
                    reports += Error(
                        compKeyword.pos, "Cannot declare nested companion declaration", "Encountered first one here"
                    )
                    reports += Error(
                        comp.pos,
                        "Cannot declare nested companion declaration",
                        "Nested `comp` keyword declaration occurs here"
                    )
                }

                assertUntil(TokenType.OpenBrace)
                fields += parseFields(classReference, comp)
                assertUntil(TokenType.CloseBrace)
            } else if (peekIf(Token::isAccessorKeyword) || peekIf(Token::isMutKeyword)) {
                // Scoped-modified fields
                // (`pub`#1 / `prot` / `intl` / `priv`)? (`mut`)? :
                // Assume it's accessor keyword or mut keyword
                val (accessor, abstr, _, mutable) = parseModifiers(
                    ovrdKeyword = false, forbidPubAccessor = false
                ) { it.type == TokenType.Colon }

                accessorToken = accessor
                abstrToken = abstr
                mutKeyword = mutable

                assertUntil(TokenType.Colon)

                val currentFlag =
                    HasFlag.getFlag(Accessor.fromString(accessorToken?.literal), abstrToken != null, mutKeyword != null)

                if (!usedFlags.add(currentFlag)) {
                    reports += Error(
                        Position.fromMultipleAndExtend(accessorToken?.pos, mutKeyword?.pos),
                        "Duplicate access flags",
                        "Try merge current fields back to same exist access block"
                    )
                }
            } else {
                // Must be a field
                val name = assertUntil(TokenType.Identifier)
                assertUntil(TokenType.Colon)
                val typeReference = parseComplexTypeSymbol()

                val field = Field(
                    classReference, accessorToken, abstrToken, mutKeyword, compKeyword, name, typeReference
                )

                if (!fields.add(field)) {
                    reports += Error(
                        name?.pos,
                        "Field ${name?.literal ?: "<Unknown>"} has already declared in same context",
                        "Try rename this field"
                    )
                }
            }
        }

        return fields.toList()
    }

    private fun parseFunctions(
        classReference: Reference?, parentReference: Reference?
    ): Triple<List<Function>, List<Constructor>, List<Statement>> {
        var firstCompKeyword: Token? = null
        var companionBlock: List<Statement>? = null
        val functions = object : MutableObjectSet<Function>() {
            override fun isDuplicate(a: Function, b: Function): Boolean =
                a.name?.literal == b.name?.literal && a.parameters == b.parameters
        }
        val constructors = object : MutableObjectSet<Constructor>() {
            override fun isDuplicate(a: Constructor, b: Constructor): Boolean = a.parameters == b.parameters
        }

        while (hasNext()) {
            if (peekIf(TokenType.CloseBrace)) break

            val (accessor, abstr, ovrd, mutable) = parseModifiers { it.isNewKeyword() || it.isFnKeyword() || it.isCompKeyword() || it.type == TokenType.CloseBrace }

            if (peekIf(Token::isCompKeyword)) {
                // Companion block
                if (accessor != null) {
                    // Companion block with access modifier
                    reports += Error(
                        accessor.pos,
                        "Cannot declare companion block with access modifier `${accessor.literal}`",
                        "Remove this access modifier `${accessor.literal}`"
                    )
                }

                if (ovrd != null) {
                    // Companion block with `ovrd` keyword
                    reports += Error(
                        ovrd.pos, "Cannot declare companion block with `ovrd` keyword", "Remove this `ovrd` keyword"
                    )
                }

                if (mutable != null) {
                    // Companion block with `mut` keyword
                    reports += Error(
                        mutable.pos, "Cannot declare companion block with `mut` keyword", "Remove this `mut` keyword"
                    )
                }

                val compKeyword = next()

                assertUntil(TokenType.OpenBrace)

                if (companionBlock != null) {
                    // Companion block already declared
                    reports += Error(
                        compKeyword?.pos,
                        "Companion block already declared",
                        "Try merge companion blocks together"
                    )
                    reports += Error(
                        firstCompKeyword?.pos,
                        "Companion block already declared",
                        "Merge to this companion block"
                    )
                } else firstCompKeyword = compKeyword

                companionBlock = parseStatements(true)

                assertUntil(TokenType.CloseBrace)
            } else if (peekIf(Token::isNewKeyword)) {
                // Constructor declaration
                if (ovrd != null) {
                    // Constructor with `ovrd` keyword
                    reports += Error(
                        ovrd.pos, "Cannot declare constructor with `ovrd` keyword", "Remove this `ovrd` keyword"
                    )
                }

                if (mutable != null) {
                    // Constructor with `mut` keyword
                    reports += Error(
                        mutable.pos, "Cannot declare constructor with `mut` keyword", "Remove this `mut` keyword"
                    )
                }

                val newKeyword = next() // `new` keyword

                val (parameterSelfKeyword, parameters) = parseParameters()

                if (parameterSelfKeyword != null) {
                    reports += Error(
                        parameterSelfKeyword.pos,
                        "Constructor has already added `self` variable under the hood",
                        "Remove `self` keyword"
                    )
                }

                var superKeyword: Token? = null
                var selfKeyword: Token? = null
                var statements: List<Statement>? = null

                val superCallArguments = if (peekIf(TokenType.Colon)) {
                    // `super` call
                    consume()
                    if (peekIf(Token::isSuperKeyword)) superKeyword = next()
                    else if (peekIf(Token::isSelfKeyword)) selfKeyword = next()
                    else reports += Error(
                        nextOrLast()?.pos, "Unexpected token, expected `super` or `self` keyword"
                    )

                    assertUntil(TokenType.OpenParenthesis)
                    val arguments = parseArguments(true)
                    assertUntil(TokenType.CloseParenthesis)

                    arguments
                } else listOf()

                if (peekIf(TokenType.OpenBrace)) {
                    consume() // open brace
                    statements = parseStatements()
                    assertUntil(TokenType.CloseBrace)
                }

                val constructor = Constructor(
                    classReference,
                    parentReference,
                    accessor,
                    newKeyword,
                    parameters,
                    statements ?: listOf(),
                    superKeyword,
                    selfKeyword,
                    superCallArguments
                )

                if (!constructors.add(constructor)) {
                    // Duplicate constructors
                    reports += Error(
                        newKeyword?.pos,
                        "Constructor `new`(${DisplayFactory.getParametersTypePretty(parameters)}) has already declared in same context",
                        "Try modify parameters' type"
                    )
                }
            } else if (peekIf(Token::isFnKeyword)) {
                // Function declaration
                consume() // `fn` keyword

                val name = assertUntil(TokenType.Identifier)
                val (parameterSelfKeyword, parameters) = parseParameters()

                val returnType = if (peekIf(TokenType.Colon)) {
                    consume()
                    parseComplexTypeSymbol()
                } else null

                val statements = if (peekIf(TokenType.OpenBrace)) {
                    consume() // open brace
                    val statements = parseStatements(parameterSelfKeyword != null)
                    assertUntil(TokenType.CloseBrace)

                    statements
                } else null

                val function = Function(
                    classReference,
                    accessor,
                    ovrd,
                    abstr,
                    mutable,
                    parameterSelfKeyword,
                    name,
                    parameters,
                    returnType,
                    statements,
                )

                if (!functions.add(function)) {
                    // Duplicate functions
                    reports += Error(
                        name?.pos,
                        "Function ${name?.literal}(${DisplayFactory.getParametersTypePretty(parameters)}) has already declared in same context",
                        "Try rename this function or modify parameters' type"
                    )
                }
            } else if (peekIf(TokenType.CloseBrace)) break
        }

        return Triple(functions.toList(), constructors.toList(), companionBlock ?: listOf())
    }

    /**
     * Returns list of parameters and an optional `self` keyword token to determine whether owner function is companion or not
     */
    private fun parseParameters(): Pair<Token?, List<Parameter>> {
        var selfToken: Token? = null
        val parameters = mutableListOf<Parameter>()

        assertUntil(TokenType.OpenParenthesis)
        while (hasNext()) {
            if (peekIf(TokenType.CloseParenthesis)) break

            // self parameter, refers to owner class object
            val parameterName = assertUntil(TokenType.Identifier) ?: break // break if pos reached end of file

            if (parameterName.isSelfKeyword()) {
                if (parameters.isNotEmpty()) {
                    // `self` declared after other parameters
                    reports += Error(
                        parameterName.pos,
                        "`self` can only be at the start of parameters",
                        "move `self` to the start of parameters"
                    )
                } else selfToken = parameterName
            } else {
                //
                val colon = assertUntil(TokenType.Colon)
                val type = parseComplexTypeSymbol()

                parameters += Parameter(
                    parameterName, colon, type
                )
            }

            if (peekIf(TokenType.Comma)) {
                consume()
            } else break
        }
        assertUntil(TokenType.CloseParenthesis)

        return selfToken to parameters
    }

    private fun parseStatements(inCompanionContext: Boolean = false): List<Statement> {
        val statements = mutableListOf<Statement>()

        while (hasNext()) {
            if (peekIf(TokenType.CloseBrace)) break

            statements += parseStatement(inCompanionContext) ?: continue

            if (peekIf(TokenType.SemiColon)) {
                val semiColonToken = next()!!

                reports += Error(
                    semiColonToken.pos, "Redundant semicolon after statement", "Remove this semicolon"
                )
            }
        }

        return statements
    }

    private fun parseStatement(inCompanionContext: Boolean = false): Statement? {
        val mutKeyword = if (peekIf(Token::isMutKeyword)) next()
        else null

        // Situations:
        // after optional `mut` keyword:
        // a := will be identified as variable declaration
        // a, (mut | ID) will be also identified as variable declaration
        if (peekMultiple(TokenType.Identifier, TokenType.ColonEqual) || peekMultiple(
                TokenType.Identifier,
                TokenType.Comma
            )
        ) {
            // Variable declaration
            // mut a := 1
            // mut a, b := 1
            // a, b := 1
            // mut a, mut b := 1
            val variables = mutableListOf(mutKeyword to next())

            while (hasNext()) {
                if (peekIf(TokenType.Comma)) {
                    consume()

                    val mutableKeyword = if (peekIf(Token::isMutKeyword)) next()
                    else null
                    val name = assertUntil(TokenType.Identifier)

                    variables.add(mutableKeyword to name)
                } else break
            }

            val operator = next()

            val expressions = mutableListOf(parseExpression(inCompanionContext))

            while (hasNext()) {
                if (peekIf(TokenType.Comma)) {
                    consume()

                    expressions += parseExpression(inCompanionContext)
                } else break
            }

            return VariableDeclaration(
                variables, operator, expressions
            )
        }

        return if (mutKeyword != null) {
            reports += Error(
                mutKeyword.pos, "Unexpected `mut` keyword", "Do you mean to declare variable?"
            )
            return null
        } else if (peekIf(Token::isReturnKeyword)) {
            // Return statement

            val returnKeyword = next()
            val expression = parseExpression(inCompanionContext, true)

            ReturnStatement(expression, pos = returnKeyword?.pos?.extend(expression?.pos))
        } else if (peekIf(Token::isIfKeyword)) {
            // if-else statement
            val ifKeyword = next()
            val condition = parseExpression(inCompanionContext, true)

            val trueStatement = parseStatement(inCompanionContext)

            val elseStatement = if (peekIf(Token::isElseKeyword)) {
                consume()
                parseStatement(inCompanionContext)
            } else null

            IfStatement(
                condition, trueStatement, elseStatement, ifKeyword?.pos?.extendSelf(trueStatement?.pos)
            )
        } else if (peekIf(TokenType.OpenBrace)) {
            // Block statement
            val openBrace = next()
            val statements = parseStatements(inCompanionContext)
            val closeBrace = assertUntil(TokenType.CloseBrace)

            BlockStatement(
                statements, openBrace?.pos?.extendSelf(closeBrace?.pos)
            )
        } else if (peekIf(Token::isForKeyword)) {
            // Java-style For loop
            val forKeyword = next()
            val initStatement = if (!peekIf(TokenType.SemiColon)) parseStatement(inCompanionContext) else null

            assertUntil(TokenType.SemiColon)

            val condition = if (!peekIf(TokenType.SemiColon)) parseExpression(inCompanionContext, true) else null

            assertUntil(TokenType.SemiColon)

            val postExpression = if (!peekIf(TokenType.OpenBrace)) parseExpression(inCompanionContext) else null

            assertUntil(TokenType.OpenBrace)

            val statements = mutableListOf<Statement?>()

            while (!peekIf(TokenType.CloseBrace)) {
                statements += parseStatement(inCompanionContext)
            }

            assertUntil(TokenType.CloseBrace)

            JForStatement(
                initStatement, condition, postExpression, statements, forKeyword?.pos
            )
        } else {
            val expression = parseExpression(inCompanionContext)

            if (expression != null) ExpressionStatement(expression)
            else null
        }
    }

    private fun parseExpression(inCompanionContext: Boolean, retainValue: Boolean = false): Expression? =
        parseAssignment(inCompanionContext, retainValue)

    private fun parseAssignment(inCompanionContext: Boolean, retainValue: Boolean): Expression? {
        var expression = parseBinaryExpression(0, inCompanionContext, retainValue)

        if (expression is IdentifierCallExpression && (peekIf(TokenType.DoublePlus) || peekIf(TokenType.DoubleMinus))) {
            val operator = next()
            expression = UnaryExpression(
                operator, expression, true, retainValue, expression.copy().pos?.extend(operator?.pos)
            )
        }

        while (peekIf(TokenType.Equal)) {
            // Assignment
            val operator = next()

            val rightExpression = parseExpression(inCompanionContext, true)

            expression = AssignmentExpression(expression, operator, rightExpression, retainValue)
        }

        return expression
    }

    private fun parseBinaryExpression(
        parentPrecedence: Int = 0, inCompanionContext: Boolean, retainValue: Boolean
    ): Expression? {
        var left: Expression?
        val unaryPrecedence = peek()?.type?.unaryPrecedence() ?: 0

        left = if (unaryPrecedence != 0 && unaryPrecedence >= parentPrecedence) {
            val operator = next()
            val right = parseBinaryExpression(unaryPrecedence, inCompanionContext, retainValue)

            UnaryExpression(operator, right, retainValue = retainValue)
        } else parsePrimaryExpression(inCompanionContext)

        while (true) {
            val binaryPrecedence = peek()?.type?.binaryPrecedence() ?: 0

            if (binaryPrecedence == 0 || binaryPrecedence <= parentPrecedence) break

            val operator = next()
            val right = parseBinaryExpression(binaryPrecedence, inCompanionContext, retainValue)

            left = BinaryExpression(
                left, operator, right
            )
        }

        return left
    }

    private fun parsePrimaryExpression(inCompanionContext: Boolean): Expression? {
        var expression = when (peek()?.type) {
            TokenType.IntegerLiteral -> IntegerLiteral(next())
            TokenType.FloatLiteral -> FloatLiteral(next())
            TokenType.CharLiteral -> CharLiteral(next())
            TokenType.StringLiteral -> StrLiteral(next())
            TokenType.Identifier -> when (peek()?.literal) {
                "true", "false" -> BoolLiteral(next())
                "null" -> NullLiteral(next())
                "new" -> parseConstructorExpression(inCompanionContext)
                "if" -> parseIfExpression(inCompanionContext)
                else -> parseSecondaryExpression(inCompanionContext)
            }
            TokenType.OpenBracket -> parseArrayExpression(inCompanionContext)
            TokenType.OpenParenthesis -> {
                consume()

                val expression = parseExpression(inCompanionContext)

                assertUntil(TokenType.CloseParenthesis)

                ParenthesizedExpression(expression)
            }
            else -> {
                val token = nextOrLast()

                if (token != null) reports.reportUnexpectedToken(token)
                else reports += Error("Unexpected token <Unknown>")

                null
            }
        }

        while (peekIf(TokenType.Dot) || peekIf(TokenType.OpenBracket) || peekIf(Token::isAsKeyword)) {
            if (peekIf(TokenType.Dot)) {
                // Chain calling
                consume()

                val name = assertUntil(TokenType.Identifier)

                if (peekIf(TokenType.OpenParenthesis)) {
                    // Member function
                    // owner.memberFunction()
                    consume()

                    val arguments = parseArguments(inCompanionContext)

                    assertUntil(TokenType.CloseParenthesis)

                    val pos = Position.fromMultipleAndExtend(
                        name?.pos, arguments.lastOrNull()?.pos
                    )?.extendSelf() // extend additional 1 character for `)`

                    expression = FunctionCallExpression(
                        null, name, arguments, inCompanionContext, previousExpression = expression, pos = pos
                    )
                } else {
                    // Member field
                    // owner.memberField
                    expression = IdentifierCallExpression(
                        null, name, previousExpression = expression, pos = name?.pos?.extend(name.pos)
                    )
                }
            } else if (peekIf(TokenType.OpenBracket)) {
                // Index expression
                consume()

                val indexExpression = parseExpression(inCompanionContext, true)

                val closeBracket = assertUntil(TokenType.CloseBracket)

                expression = IndexExpression(
                    expression, indexExpression, pos = Position.fromMultipleAndExtend(
                        expression?.pos, closeBracket?.pos
                    )
                )
            } else if (peekIf(Token::isAsKeyword)) {
                // As expression
                consume()

                val targetTypeReference = parseTypeSymbol()

                expression = AsExpression(
                    expression, targetTypeReference
                )
            }
        }

        return expression
    }

    private fun parseConstructorExpression(inCompanionContext: Boolean): Expression {
        assertUntil(TokenType.Identifier) // `new` keyword
        val ownerReference = parseTypeSymbol()
        assertUntil(TokenType.OpenParenthesis)
        val arguments = parseArguments(inCompanionContext)
        assertUntil(TokenType.CloseParenthesis)

        return ConstructorCallExpression(ownerReference, arguments)
    }

    private fun parseIfExpression(inCompanionContext: Boolean): Expression {
        val ifKeyword = next()
        val condition = parseExpression(inCompanionContext, true)

        val trueStatement = parseStatement(inCompanionContext)

        val elseStatement = if (peekIf(Token::isElseKeyword)) {
            consume()
            parseStatement(inCompanionContext)
        } else null

        return IfExpression(
            condition, trueStatement, elseStatement, Position.fromMultipleAndExtend(
                ifKeyword?.pos, trueStatement?.pos, elseStatement?.pos
            )
        )
    }

    private fun parseSecondaryExpression(inCompanionContext: Boolean): Expression {
        val name = next()

        return if (peekIf(TokenType.OpenParenthesis)) {
            // Function Call
            consume()

            val arguments = parseArguments(inCompanionContext)

            assertUntil(TokenType.CloseParenthesis)

            FunctionCallExpression(null, name, arguments, inCompanionContext)
        } else if (peekIf(TokenType.DoubleColon)) {
            // Companion member calling, e.g. path::Class.field, path::Class.func(...)
            // TODO: This also might be id::class.class
            consume()
            val classPath = parseTypeSymbol()

            // Prepend previous identifier to classPath as top package
            classPath.prepend(name?.literal ?: "")

            assertUntil(TokenType.Dot)

            val memberName = assertUntil(TokenType.Identifier)

            if (peekIf(TokenType.OpenParenthesis)) {
                // Companion function calling
                consume()

                val arguments = parseArguments(inCompanionContext)

                assertUntil(TokenType.CloseParenthesis)

                val pos = name?.pos?.extend(arguments.lastOrNull()?.pos)?.extendSelf()

                FunctionCallExpression(classPath, memberName, arguments, inCompanionContext, pos = pos)
            } else {
                // Companion field calling
                val pos = name?.pos?.extend(memberName?.pos)

                IdentifierCallExpression(classPath, memberName, pos = pos)
            }
        } else {
            if (peekIf(TokenType.Dot)) {
                // Companion member calling, e.g. Class.field, Class.func()
                IdentifierCallExpression(null, name)
            } else {
                // Local identifier Call, e.g local variables, local class fields
                IdentifierCallExpression(null, name)
            }
        }
    }

    private fun parseArrayExpression(inCompanionContext: Boolean): Expression? {
        // A valid array expression can be in the following forms:
        // Note: size and initial elements cannot exist at the same time
        // 1. array declaration
        // e.g. [[i32;10];]{}
        //      [i32;10]{}
        // or
        //      [[i32;10];]{}
        // 3. array initialization
        // e.g. [[;];]{ [;]{ 1 } }
        //      [;]{ 1 }

        fun collectArrayElements(): Pair<List<Expression?>, Token?> {
            val elements = mutableListOf<Expression?>()

            while (!peekIf(TokenType.CloseBrace)) {
                elements += parseExpression(inCompanionContext, true)

                if (peekIf(TokenType.Comma)) consume()
                else break
            }

            val closeBrace = assertUntil(TokenType.CloseBrace)

            return elements to closeBrace
        }

        val firstBracketPos = peek()?.pos
        var arrayDimensionCounter = 0

        while (peekIf(TokenType.OpenBracket)) {
            consume()
            arrayDimensionCounter++
        }

        val arrayDimensionExpressions = MutableList<Expression?>(arrayDimensionCounter) { null }
        val arrayDimension = arrayDimensionCounter
        val baseType = if (peekIf(TokenType.SemiColon)) null
        else parseTypeSymbol()

        while (arrayDimensionCounter != 0) {
            assertUntil(TokenType.SemiColon)

            if (!peekIf(TokenType.CloseBracket)) {
                arrayDimensionExpressions[arrayDimensionCounter - 1] = parseExpression(inCompanionContext, true)
            }

            assertUntil(TokenType.CloseBracket)
            arrayDimensionCounter--
        }

        assertUntil(TokenType.OpenBrace)

        val (elementExpressions, closeBracket) = collectArrayElements()
        val arrayExpressionPos = firstBracketPos?.extend(closeBracket?.pos)

        if (arrayDimensionExpressions.any { it != null } && elementExpressions.isNotEmpty()) {
            reports += Error(
                arrayExpressionPos,
                "Cannot initialize array with both dimension expression and element expressions",
                "Remove either all dimension expressions or all element expressions"
            )
        }

        return if (elementExpressions.isNotEmpty()) {
            // Array initialization
            ArrayInitialization(null, elementExpressions, arrayExpressionPos)
        } else if (baseType != null && arrayDimensionExpressions.isNotEmpty()) {
            // Array declaration
            baseType.appendArrayDimension(arrayDimension)

            ArrayDeclaration(baseType, arrayDimensionExpressions, arrayExpressionPos)
        } else {
            reports += Error(
                arrayExpressionPos, "Cannot declare array without type info or top-level dimension expression"
            )

            null
        }
    }

    private fun parseArguments(inCompanionContext: Boolean): List<Expression?> {
        val arguments = arrayListOf<Expression?>()

        while (!peekIf(TokenType.CloseParenthesis)) {
            arguments += parseExpression(inCompanionContext, true)

            if (peekIf(TokenType.Comma)) {
                consume()
            } else break
        }

        return arguments
    }
}