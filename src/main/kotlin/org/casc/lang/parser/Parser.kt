package org.casc.lang.parser

import org.casc.lang.ast.*
import org.casc.lang.ast.Function
import org.casc.lang.compilation.AbstractPreference
import org.casc.lang.compilation.Error
import org.casc.lang.compilation.Report
import org.casc.lang.compilation.Warning
import org.casc.lang.table.Reference
import org.casc.lang.utils.MutableObjectSet
import org.objectweb.asm.Opcodes

class Parser(private val preference: AbstractPreference) {
    private var pos: Int = 0
    private var reports: MutableList<Report> = mutableListOf()
    private lateinit var tokens: List<Token>

    fun parse(path: String, relativeFilePath: String, tokens: List<Token>): Pair<List<Report>, File> {
        reports.clear()
        pos = 0
        this.tokens = tokens
        val file = parseFile(path, relativeFilePath)

        return reports to file
    }

    // ================================
    // PRIVATE UTILITY FUNCTIONS
    // ================================

    private fun hasNext(): Boolean =
        tokens.lastIndex == pos

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
                tokens[pos].pos,
                "Unexpected token ${tokens[pos++].type}, expected predicate $predicate"
            )
            null
        }
    }

    private fun peek(offset: Int = 0): Token? = when {
        tokens.isEmpty() -> null
        pos + offset >= tokens.size -> null
        else -> tokens[pos + offset]
    }

    private inline fun peekIf(predicate: (Token) -> Boolean, offset: Int = 0): Boolean =
        peek(offset)?.let {
            predicate(it)
        } ?: false

    private fun peekIf(type: TokenType, offset: Int = 0): Boolean =
        peek(offset)?.let {
            it.type == type
        } ?: false

    /**
     * peekMultiple is used to determine whether a set of tokens matches specific syntax pattern.
     */
    private fun peekMultiple(offset: Int, vararg types: TokenType): Boolean {
        if (offset <= 0 || types.size != offset) return false

        for (i in 0 until offset) {
            if (peek(i)?.type != types[i]) return false
        }

        return true
    }

    private fun last(): Token? =
        peek(-1)

    private fun next(): Token? = when {
        !hasNext() -> null
        else -> tokens[pos++]
    }

    /**
     * Get next token if there are more than 1 tokens remaining, otherwise return last token if it exists
     */
    private fun nextOrLast(): Token? =
        if (hasNext()) next() else tokens.lastOrNull()

    private fun consume() {
        if (hasNext()) pos++
    }

    // ================================
    // PARSING FUNCTIONS
    // ================================

    private fun parseFile(path: String, relativeFilePath: String): File {
        // Parse optional package declaration
        val packageReference = if (peekIf(Token::isPackageKeyword)) {
            consume()
            parseTypeSymbol()
        } else null

        // Parse usages
        val usages = mutableListOf<Reference?>()

        while (peekIf(Token::isUseKeyword)) {
            consume()
            usages += parseUsage()
        }

        // Parse class declaration
        val modifiers = object : MutableObjectSet<Token>() {
            override fun isDuplicate(a: Token, b: Token): Boolean =
                a.literal == b.literal
        }

        while (!peekIf(Token::isClassKeyword)) {
            val nextToken = next()

            if (nextToken != null) {
                if (modifiers.find(Token::isAccessorKeyword) != null && nextToken.isAccessorKeyword()) {
                    // More than two access modifiers
                    reports += Error(
                        nextToken.pos,
                        "Cannot have more than two access modifiers or different access modifiers at same time",
                        "Remove this"
                    )
                }

                if (nextToken.literal == "pub") {
                    // Declared with `pub`
                    reports += Warning(
                        nextToken.pos,
                        "Redundant `pub` keyword, all members' default access modifier is `pub`",
                        "You can safely remove `pub`"
                    )
                }

                if (modifiers.find(Token::isMutKeyword) != null && nextToken.isAccessorKeyword()) {
                    // Wrong modifier sequence
                    reports += Error(
                        nextToken.pos,
                        "Cannot declare access modifier after `mut` declared",
                        "Try move this modifier before `mut`"
                    )
                }

                if (!modifiers.add(nextToken)) {
                    // Duplicate modifiers
                    reports += Error(
                        nextToken.pos,
                        "Duplicate modifiers",
                        "Remove this modifier"
                    )
                }
            }
        }

        val classKeyword = assert(Token::isClassKeyword)
        val className = assert(TokenType.Identifier)
        val classReference =
            if (className != null) Reference(
                "${packageReference?.fullQualifiedPath?.let { "${it}/" } ?: ""}${className.literal}",
                className.literal,
                pos = className.pos
            )
            else null
        var fields = listOf<Field>()

        if (peekIf(TokenType.OpenBrace)) { // Member declaration is optional
            assert(TokenType.OpenBrace)
            fields = parseFields(usages, classReference)
            assert(TokenType.CloseBrace)
        }

        // Parse major implementation
        var parentClassReference: Reference? = null
        var functions = listOf<Function>()
        var constructors = listOf<Constructor>()
        var companionBlock = listOf<Statement?>()

        if (peekIf(Token::isImplKeyword)) {
            next()
            val implName = assert(TokenType.Identifier)

            if (implName?.literal != className?.literal) {
                reports += Error(
                    last()!!.pos,
                    "Unexpected implementation for class ${implName?.literal}"
                )
            }

            if (peekIf(TokenType.Colon)) {
                // Class inheritance
                consume()
                parentClassReference = parseTypeSymbol()
            }

            if (peekIf(TokenType.OpenBrace)) {
                consume()
                val (fns, ctors, compBlocks) = parseFunctions(classReference, parentClassReference)
                functions = fns
                constructors = ctors
                companionBlock = compBlocks
                assert(TokenType.CloseBrace)
            }
        }

        // TODO: Abstract class inheritance & interface implementation etc. WIP
        val clazz = Class(
            packageReference,
            usages,
            parentClassReference,
            listOf(),
            modifiers.find(Token::isAccessorKeyword),
            modifiers.find(Token::isMutKeyword),
            classKeyword,
            className,
            fields,
            companionBlock,
            constructors,
            functions
        )

        return File(path, relativeFilePath, clazz)
    }

    /**
     * Used to parse type symbol (excluding array type symbol), not designed to be parsed with `use` syntax.
     */
    private fun parseTypeSymbol(): Reference {
        var currentIdentifier = assert(TokenType.Identifier)
        val tokens = mutableListOf<Token?>()
        var nameBuilder = currentIdentifier?.literal

        tokens += currentIdentifier

        while (peekIf(TokenType.DoubleColon)) {
            consume()
            currentIdentifier = assert(TokenType.Identifier)

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
            assertUntil(TokenType.CloseBracket) ?: break

            arrayDimensionCounter--
        }

        baseTypeSymbol.appendArrayDimension(arrayDimension)

        return baseTypeSymbol
    }

    private fun parseUsage(prependReference: Reference? = null): List<Reference> {
        val references = mutableListOf<Reference>()
        val reference = parseTypeSymbol()

        if (peekMultiple(2, TokenType.DoubleColon, TokenType.OpenBrace)) {
            consume()
            consume()

            while (!peekIf(TokenType.CloseBrace)) {
                references += parseUsage(reference)

                if (peekIf(TokenType.Comma)) {
                    consume()
                    continue
                } else break
            }

            assert(TokenType.CloseBrace)
        } else if (peekIf(TokenType.Identifier) && peekIf(Token::isAsKeyword)) {
            consume()
            val aliasReference = assertUntil(TokenType.Identifier)

            if (prependReference != null) {
                prependReference.append(reference.fullQualifiedPath)
                prependReference.className = aliasReference?.literal ?: ""

                references += prependReference
            } else {
                reference.className = aliasReference?.literal ?: ""

                references += reference
            }
        } else references += reference

        return references
    }

    /**
     * parseModifiers follows the following modifier sequence:
     * (`pub`#1 / `prot` / `intl` / `priv`)? (`ovrd`)? (`mut`)?
     *
     * #1: Will generate a warning by default.
     */
    private fun parseModifiers(accessModifier: Boolean = true, mutableKeyword: Boolean = true, ovrdKeyword: Boolean = true, terminator: (Token) -> Boolean): Triple<Token?, Token?, Token?> {
        var accessor: Token? = null
        var ovrd: Token? = null
        var mutable: Token? = null

        while (hasNext()) {
            val token = next()!!

            if (token.isAccessorKeyword() && accessModifier) {
                if (accessor != null) {
                    reports += Error(
                        accessor.pos,
                        "Duplicate access modifier `${accessor.literal}`",
                        "Encountered first one here"
                    )
                    reports += Error(
                        token.pos,
                        "Duplicate access modifier `${token.literal}`",
                        "Duplicate here"
                    )
                }

                if (ovrdKeyword && ovrd != null) {
                    reports += Error(
                        ovrd.pos,
                        "Cannot declare access modifier after `ovrd` keyword",
                        "`ovrd` keyword here"
                    )
                }
                if (mutableKeyword && mutable != null) {
                    reports += Error(
                        mutable.pos,
                        "Cannot declare access modifier after `mut` keyword",
                        "`mut` keyword here"
                    )
                }

                accessor = token
            } else if (token.isOvrdKeyword() && ovrdKeyword) {
                if (ovrd != null) {
                    reports += Error(
                        ovrd.pos,
                        "Duplicate `ovrd` keyword",
                        "Encountered first one here"
                    )
                    reports += Error(
                        token.pos,
                        "Duplicate `ovrd` keyword",
                        "Duplicate here"
                    )
                }

                if (mutableKeyword && mutable != null) {
                    reports += Error(
                        mutable.pos,
                        "Cannot declare `ovrd` keyword after `mut` keyword",
                        "`mut` keyword here"
                    )
                }

                ovrd = token
            }else if (token.isMutKeyword() && mutableKeyword) {
                if (mutable != null) {
                    reports += Error(
                        mutable.pos,
                        "Duplicate `mut` keyword",
                        "Encountered first one here"
                    )
                    reports += Error(
                        token.pos,
                        "Duplicate `mut` keyword",
                        "Duplicate here"
                    )
                }

                mutable = token
            } else if (terminator(token)) {
                break
            } else {
                var builder = if (accessModifier) "access modifiers (`pub`, `prot`, `intl`, `priv`)" else ""
                builder += if (builder.isNotEmpty() && ovrdKeyword) "or `ovrd` keyword" else if (ovrdKeyword) "`ovrd` keyword" else ""
                builder += if (builder.isNotEmpty() && mutableKeyword) "or `mut` keyword" else if (mutableKeyword) "`mut` keyword" else ""

                reports += Error(
                    token.pos,
                    "Unexpected token ${token.type}, expected $builder"
                )
            }
        }

        return Triple(accessor, ovrd, mutable)
    }

    private fun parseFields(
        usages: List<Reference?>,
        classReference: Reference?,
        compKeyword: Token? = null
    ): List<Field> {
        var accessorToken: Token? = null
        var mutKeyword: Token? = null
        var compScopeDeclared = false
        val usedFlags = mutableSetOf(Opcodes.ACC_PUBLIC + Opcodes.ACC_FINAL) // Default flag is pub (final)
        val fields = object : MutableObjectSet<Field>() {
            override fun isDuplicate(a: Field, b: Field): Boolean =
                a.name?.literal == b.name?.literal
        }

        while (!peekIf(TokenType.CloseBrace)) {
            if (peekIf(Token::isCompKeyword)) {
                val comp = next()!!

                if (compScopeDeclared) {
                    reports += Warning(
                        comp.pos,
                        "Companion declaration scope has been declared once"
                    )
                } else compScopeDeclared = true

                if (compKeyword != null) {
                    reports += Error(
                        comp.pos,
                        "Cannot declare nested companion declaration"
                    )
                }

                assert(TokenType.OpenBrace)
                fields += parseFields(usages, classReference, comp)
                assert(TokenType.CloseBrace)
            } else if (peekIf(Token::isAccessorKeyword) || peekIf(Token::isMutKeyword)) {
                // Assume it's accessor keyword or mut keyword
                if (peekIf(Token::isAccessorKeyword)) accessorToken = next()
                if (peekIf(Token::isMutKeyword)) mutKeyword = next()
                assert(TokenType.Colon)

                val currentFlag =
                    Accessor.fromString(accessorToken?.literal).access + (mutKeyword?.let { 0 } ?: Opcodes.ACC_FINAL)

                if (!usedFlags.add(currentFlag)) {
                    reports += Error(
                        accessorToken?.pos ?: mutKeyword?.pos,
                        "Duplicate access flags",
                        "Try merge current fields back to same exist access block"
                    )
                }
            } else {
                // Must be a field
                val name = assert(TokenType.Identifier)
                assert(TokenType.Colon)
                val typeReference = parseComplexTypeSymbol()

                val field = Field(
                    classReference,
                    accessorToken,
                    mutKeyword,
                    compKeyword,
                    name,
                    typeReference
                )

                if (!fields.add(field)) {
                    reports += Error(
                        name?.pos,
                        "Field ${name?.literal} has already declared in same context",
                        "Try rename this field"
                    )
                }
            }
        }

        return fields.toList()
    }

    private fun parseFunctions(
        classReference: Reference?,
        parentReference: Reference?
    ): Triple<List<Function>, List<Constructor>, List<Statement?>> {
        val companionBlock = mutableListOf<Statement?>()
        val functions = object : MutableObjectSet<Function>() {
            override fun isDuplicate(a: Function, b: Function): Boolean =
                a.name?.literal == b.name?.literal && a.parameters == b.parameters
        }
        val constructors = object : MutableObjectSet<Constructor>() {
            override fun isDuplicate(a: Constructor, b: Constructor): Boolean =
                a.parameters == b.parameters
        }

        while (hasNext()) {
            if (peekIf(TokenType.CloseBrace)) break

            if (peekIf(Token::isCompKeyword)) {
                val compKeyword = next()

                assert(TokenType.OpenBrace)
                // Companion block
                if (companionBlock.isNotEmpty()) {
                    // Companion block already declared
                    reports += Error(
                        compKeyword?.pos,
                        "Companion block already declared",
                        "Try merge companion blocks together"
                    )
                }

                companionBlock += parseStatements(true)

                assert(TokenType.CloseBrace)
            }

            val (accessor, mutable, ovrd) = parseModifiers { it.isNewKeyword() || it.isFnKeyword() || it.type == TokenType.CloseBrace }

            if (peekIf(Token::isNewKeyword)) {
                // Constructor declaration
                if (ovrd != null) {
                    // Constructor with `ovrd` keyword
                    reports += Error(
                        ovrd.pos,
                        "Cannot declare constructor with `ovrd` keyword",
                        "Remove this `ovrd` keyword"
                    )
                }

                if (mutable != null) {
                    // Constructor with `mut` keyword
                    reports += Error(
                        mutable.pos,
                        "Cannot declare constructor with `mut` keyword",
                        "Remove this `mut` keyword"
                    )
                }

                val newKeyword = next() // `new` keyword

                assertUntil(TokenType.OpenParenthesis)
                val (parameters, parameterSelfKeyword) = parseParameters()

                if (parameterSelfKeyword != null) {
                    reports += Error(
                        parameterSelfKeyword.pos,
                        "constructor has already added `self` variable under the hood",
                        "Remove `self` keyword"
                    )
                }
                assertUntil(TokenType.CloseParenthesis)

                var superKeyword: Token? = null
                var selfKeyword: Token? = null
                val superCallArguments = if (peekIf(TokenType.Colon)) {
                    // `super` call
                    consume()
                    if (peekIf(Token::isSuperKeyword)) superKeyword = next()
                    else if (peekIf(Token::isSelfKeyword)) selfKeyword = next()
                    else reports += Error(
                        nextOrLast()?.pos,
                        "Unexpected token, expected `super` or `self` keyword"
                    )
                    assertUntil(TokenType.OpenParenthesis)
                    val arguments = parseArguments(true)
                    assertUntil(TokenType.CloseParenthesis)

                    arguments
                } else listOf()

                assertUntil(TokenType.OpenBrace)
                val statements = parseStatements()
                assertUntil(TokenType.CloseBrace)

                val constructor = Constructor(
                    classReference,
                    parentReference,
                    accessor,
                    newKeyword,
                    parameters,
                    statements,
                    superKeyword,
                    selfKeyword,
                    superCallArguments
                )

                if (!constructors.add(constructor)) {
                    // Duplicate constructors
                    reports += Error(
                        newKeyword?.pos,
                        "Constructor new(${DisplayFactory.getParametersTypePretty(parameters)}) has already declared in same context",
                        "Try modify parameters' type"
                    )
                }
            } else if (peekIf(Token::isFnKeyword)) {
                // Function declaration
                consume() // `fn` keyword

                val name = assert(TokenType.Identifier)
                assert(TokenType.OpenParenthesis)
                val (parameters, parameterSelfKeyword) = parseParameters()
                assert(TokenType.CloseParenthesis)

                val returnType = if (peekIf(TokenType.Colon)) {
                    consume()
                    parseComplexTypeSymbol()
                } else null

                assert(TokenType.OpenBrace)
                val statements = parseStatements(parameterSelfKeyword != null)
                assert(TokenType.CloseBrace)

                val function = Function(
                    classReference,
                    accessor,
                    ovrd,
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
                        "Function ${name?.literal}(${
                            parameters.mapNotNull { it.typeReference?.fullQualifiedPath }.joinToString()
                        }) has already declared in same context",
                        "Try rename this function or modify parameters' type"
                    )
                }
            } else if (peekIf(TokenType.CloseBrace)) break
            else {
                // Unknown declaration
                val currentToken = peek()

                reports += Error(
                    currentToken?.pos,
                    "Unexpected token `${currentToken?.literal}`"
                )
            }
        }

        return Triple(functions.toList(), constructors.toList(), companionBlock)
    }

    /**
     * Returns list of parameters and an optional `self` keyword token to determine whether owner function is companion or not
     */
    private fun parseParameters(): Pair<List<Parameter>, Token?> {
        var selfToken: Token? = null
        val parameters = mutableListOf<Parameter>()

        while (hasNext()) {
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
                    parameterName,
                    colon,
                    type
                )
            }

            if (peekIf(TokenType.Comma)) {
                consume()
            } else break
        }

        return parameters to selfToken
    }

    private fun parseStatements(inCompanionContext: Boolean = false): List<Statement> {
        val statements = mutableListOf<Statement>()

        while (!peekIf(TokenType.CloseBrace)) {
            statements += parseStatement(inCompanionContext)

            if (peekIf(TokenType.SemiColon)) {
                val semiColonToken = next()
                reports += Error(
                    semiColonToken?.pos,
                    "Redundant semicolon after statement",
                    "Remove this semicolon"
                )
            }
        }

        return statements
    }

    private fun parseStatement(inCompanionContext: Boolean = false): Statement {
        val mutKeyword =
            if (peekIf(Token::isMutKeyword)) next()
            else null

        return if (peekMultiple(2, TokenType.Identifier, TokenType.ColonEqual)) {
            // Variable declaration
            val name = next()
            val operator = next()
            val expression = parseExpression(inCompanionContext)

            VariableDeclaration(
                mutKeyword,
                name,
                operator,
                expression
            )
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
                condition,
                trueStatement,
                elseStatement,
                ifKeyword?.pos?.extend(trueStatement.pos)
            )
        } else if (peekIf(TokenType.OpenBrace)) {
            // Block statement
            val openBrace = next()
            val statements = parseStatements(inCompanionContext)
            val closeBrace = assert(TokenType.CloseBrace)

            BlockStatement(
                statements,
                openBrace?.pos?.extend(closeBrace?.pos)
            )
        } else if (peekIf(Token::isForKeyword)) {
            // Java-style For loop
            val forKeyword = next()
            val initStatement = parseStatement(inCompanionContext)

            assert(TokenType.SemiColon)

            val condition = parseExpression(inCompanionContext, true)

            assert(TokenType.SemiColon)

            val postExpression = parseExpression(inCompanionContext)
            val statement = parseStatement(inCompanionContext)

            JForStatement(
                initStatement,
                condition,
                postExpression,
                statement,
                forKeyword?.pos?.extend(statement.pos)
            )
        } else ExpressionStatement(parseExpression(inCompanionContext))
    }

    private fun parseExpression(inCompanionContext: Boolean, retainValue: Boolean = false): Expression =
        parseAssignment(inCompanionContext, retainValue)

    private fun parseAssignment(inCompanionContext: Boolean, retainValue: Boolean): Expression {
        var expression: Expression? = null

        while (expression == null) {
            expression = parseBinaryExpression(0, inCompanionContext, retainValue)

            if (expression is IdentifierCallExpression && (peekIf(TokenType.DoublePlus) || peekIf(TokenType.DoubleMinus))) {
                val operator = next()
                expression =
                    UnaryExpression(
                        operator,
                        expression,
                        true,
                        retainValue,
                        expression.copy().pos?.extend(operator?.pos)
                    )
            }

            while (peekIf(TokenType.Equal)) {
                // Assignment
                val operator = next()

                val rightExpression = parseExpression(inCompanionContext, true)

                expression = AssignmentExpression(expression, operator, rightExpression, retainValue)
            }

            if (expression == null) {
                val currentToken = next()

                reports += Error(
                    currentToken?.pos,
                    "Unexpected token ${currentToken?.type}"
                )
            }
        }

        return expression
    }

    private fun parseBinaryExpression(
        parentPrecedence: Int = 0,
        inCompanionContext: Boolean,
        retainValue: Boolean
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
                left,
                operator,
                right
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

                assert(TokenType.CloseParenthesis)

                ParenthesizedExpression(expression)
            }
            else -> null
        }

        if (expression is IdentifierCallExpression || expression is FunctionCallExpression
            || expression is ConstructorCallExpression || expression is IndexExpression
            || expression is ParenthesizedExpression
        ) {
            while (true) {
                if (peekIf(TokenType.Dot)) {
                    // Chain calling
                    consume()

                    val name = assert(TokenType.Identifier)

                    if (peekIf(TokenType.OpenParenthesis)) {
                        // Member function
                        consume()

                        val arguments = parseArguments(inCompanionContext)

                        assert(TokenType.CloseParenthesis)

                        val pos = name?.pos?.extend(arguments.lastOrNull()?.pos)?.extend()

                        expression = FunctionCallExpression(
                            null,
                            name,
                            arguments,
                            inCompanionContext,
                            previousExpression = expression,
                            pos = pos
                        )
                    } else {
                        // Member field
                        val pos = name?.pos?.extend(name.pos)

                        expression = IdentifierCallExpression(null, name, previousExpression = expression, pos = pos)
                    }
                } else if (peekIf(TokenType.OpenBracket)) {
                    // Index expression
                    consume()

                    val indexExpression = parseExpression(inCompanionContext, true)

                    val closeBracket = assert(TokenType.CloseBracket)

                    expression = IndexExpression(
                        expression,
                        indexExpression,
                        pos = expression?.pos?.extend(closeBracket?.pos)
                    )
                } else break
            }
        }

        return expression
    }

    private fun parseConstructorExpression(inCompanionContext: Boolean): Expression {
        assert(TokenType.Identifier) // `new` keyword
        val ownerReference = parseTypeSymbol()
        assert(TokenType.OpenParenthesis)
        val arguments = parseArguments(inCompanionContext)
        assert(TokenType.CloseParenthesis)

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
            condition,
            trueStatement,
            elseStatement,
            ifKeyword?.pos?.extend(trueStatement.pos)
        )
    }

    private fun parseSecondaryExpression(inCompanionContext: Boolean): Expression {
        val name = next()

        return if (peekIf(TokenType.OpenParenthesis)) {
            // Function Call
            consume()

            val arguments = parseArguments(inCompanionContext)

            assert(TokenType.CloseParenthesis)

            FunctionCallExpression(null, name, arguments, inCompanionContext)
        } else if (peekIf(TokenType.DoubleColon)) {
            // Companion member calling, e.g. path::Class.field, path::Class.func(...)
            // TODO: This also might be id::class.class
            consume()
            val classPath = parseTypeSymbol()

            // Prepend previous identifier to classPath as top package
            classPath.prepend(name?.literal ?: "")

            assert(TokenType.Dot)

            val memberName = assert(TokenType.Identifier)

            if (peekIf(TokenType.OpenParenthesis)) {
                // Companion function calling
                consume()

                val arguments = parseArguments(inCompanionContext)

                assert(TokenType.CloseParenthesis)

                val pos = name?.pos?.extend(arguments.lastOrNull()?.pos)?.extend()

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

            val closeBrace = assert(TokenType.CloseBrace)

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
        val baseType =
            if (peekIf(TokenType.SemiColon)) null
            else parseTypeSymbol()

        while (arrayDimensionCounter != 0) {
            assert(TokenType.SemiColon)

            if (!peekIf(TokenType.CloseBracket)) {
                arrayDimensionExpressions[arrayDimensionCounter - 1] = parseExpression(inCompanionContext, true)
            }

            assert(TokenType.CloseBracket)
            arrayDimensionCounter--
        }

        assert(TokenType.OpenBrace)

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
                arrayExpressionPos,
                "Cannot declare array without type info or top-level dimension expression"
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