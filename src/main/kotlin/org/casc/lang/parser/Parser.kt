package org.casc.lang.parser

import org.casc.lang.ast.*
import org.casc.lang.ast.Function
import org.casc.lang.compilation.Error
import org.casc.lang.compilation.Report
import org.casc.lang.compilation.Warning
import org.casc.lang.table.Reference

class Parser(private val lexFiles: Array<Pair<String, List<Token>>>) {
    private var pos: Int = 0
    private var reports: MutableSet<Report> = mutableSetOf()
    private lateinit var tokens: List<Token>

    fun parse(): Pair<List<Report>, List<File>> {
        val files = mutableListOf<File>()

        for ((path, tokens) in lexFiles) {
            this.tokens = tokens
            files += parseFile(path)
        }

        return reports.toList() to files
    }

    private fun assert(type: TokenType): Token? = when {
        tokens.isEmpty() -> {
            reports += Error(
                "Unable to compile empty source"
            )
            null
        }
        pos >= tokens.size -> {
            reports += Warning(
                "<Internal Compiler Error> Reached last token but parsing still goes on"
            )
            null
        }
        tokens[pos].type == type -> tokens[pos++]
        else -> {
            reports += Error(
                tokens[pos].pos,
                "Unexpected token ${tokens[pos++].type}, expected token $type"
            )
            null
        }
    }

    private fun peek(offset: Int = 0): Token? = when {
        tokens.isEmpty() -> null
        pos + offset >= tokens.size -> null
        else -> tokens[pos + offset]
    }

    /**
     * peekMultiple is used to determine whether a set of tokens matches specific syntax pattern.
     */
    private fun peekMultiple(offset: Int = 1): List<TokenType?> {
        if (offset <= 0) return listOf()

        val tokenTypes = mutableListOf<TokenType?>()

        for (i in 0 until offset) {
            tokenTypes += peek(i)?.type
        }

        return tokenTypes
    }

    private fun last(): Token? =
        peek(-1)

    private fun next(): Token? = when {
        tokens.isEmpty() -> null
        pos >= tokens.size -> null
        else -> tokens[pos++]
    }

    private fun consume() {
        if (tokens.isNotEmpty() && pos < tokens.size) pos++
    }

    private fun parseFile(path: String): File {
        // TODO: Package
        // TODO: Usages
        // Parse class declaration
        var accessor = assert(TokenType.Identifier)
        val classKeyword: Token?

        if (accessor?.isClassKeyword() == true) { // If class has no modifier, then we move accessor token to class token
            classKeyword = accessor
            accessor = null
        } else {
            classKeyword = assert(TokenType.Identifier)

            if (classKeyword?.isClassKeyword() != true) {
                reports += Error(
                    last()!!.pos,
                    "Expected class declaration"
                )
            }
        }

        val className = assert(TokenType.Identifier)
        val classReference =
            if (className != null) Reference(
                className.literal,
                className.literal,
                className.pos
            ) // TODO: Add package path to path
            else null
        assert(TokenType.OpenBrace)
        // TODO: Parse fields
        assert(TokenType.CloseBrace)

        // Parse major implementation
        val implKeyword = assert(TokenType.Identifier)

        if (implKeyword?.isImplKeyword() != true) {
            reports += Error(
                last()!!.pos,
                "Expected implementations for class"
            )
        }

        val implName = assert(TokenType.Identifier)

        if (implName?.literal != className?.literal) {
            reports += Error(
                last()!!.pos,
                "Unexpected implementation for class ${implName?.literal}"
            )
        }

        assert(TokenType.OpenBrace)
        val functions = parseFunctions(classReference)
        assert(TokenType.CloseBrace)

        val clazz = Class(accessor, classKeyword, className, functions)

        return File(path, clazz)
    }

    /**
     * Used to parse type names and usage reference
     */
    private fun parseQualifiedName(
        isParameter: Boolean = false,
        prependPath: String = "",
        startPos: Position? = null
    ): Reference? {
        var start = startPos
        var nameBuilder = ""

        while (pos < tokens.size && peek()?.type == TokenType.Identifier) {
            val identifier = assert(TokenType.Identifier)!!

            if (start == null) start = identifier.pos

            nameBuilder += identifier.literal

            if (peek()?.type == TokenType.DoubleColon) {
                consume()
                nameBuilder += "."
            } else break
        }

        if (isParameter) {
            while (pos < tokens.size && peek()?.type == TokenType.OpenBracket) {
                consume()
                assert(TokenType.CloseBracket) ?: break

                nameBuilder += "[]"
            }
        }

        return if (nameBuilder.isEmpty()) {
            null
        } else {
            val className = nameBuilder.split("::").last()

            Reference(prependPath + nameBuilder, className, start)
        }
    }

    private fun parseFunctions(classReference: Reference?, compKeyword: Token? = null): List<Function> {
        var compScopeDeclared = false
        val functions = mutableListOf<Function>()

        while (pos < tokens.size && peek()?.type != TokenType.CloseBrace) { // Break the loop after encountered class declaration's close bracket
            var accessor = assert(TokenType.Identifier)
            var mutKeyword: Token?

            if (accessor?.isCompKeyword() == true) {
                if (compScopeDeclared) {
                    reports += Warning(
                        accessor.pos,
                        "Companion declaration scope has been declared once"
                    )
                }

                if (compKeyword != null) {
                    reports += Error(
                        accessor.pos,
                        "Cannot declare nested companion declaration"
                    )
                }

                compScopeDeclared = true
                assert(TokenType.OpenBrace)
                functions += parseFunctions(classReference, accessor)
                assert(TokenType.CloseBrace)
                continue
            }

            if (accessor?.isAccessorKeyword() == true) {
                mutKeyword = next()

                if (mutKeyword?.isMutKeyword() == true) {
                    if (next()?.isFnKeyword() != true) {
                        reports.reportExpectedFnDeclaration(peek(-1))
                    }
                } else if (mutKeyword?.isFnKeyword() == true) {
                    mutKeyword = null
                } else {
                    reports.reportExpectedFnDeclaration(peek(-1))
                }
            } else if (accessor?.isMutKeyword() == true) {
                mutKeyword = accessor
                accessor = null

                if (assert(TokenType.Identifier)?.isFnKeyword() != true) {
                    reports.reportExpectedFnDeclaration(peek(-1))
                }
            } else if (accessor?.isFnKeyword() == true) {
                accessor = null
                mutKeyword = null
            } else {
                reports.reportExpectedFnDeclaration(peek(-1))

                mutKeyword = null
            }

            val name = assert(TokenType.Identifier)
            assert(TokenType.OpenParenthesis)
            val parameters = parseParameters()
            assert(TokenType.CloseParenthesis)
            val returnType: Reference?

            if (peek()?.type == TokenType.Colon) {
                consume()
                returnType = parseQualifiedName(true)
            } else {
                returnType = null
            }

            assert(TokenType.OpenBrace)
            val statements = parseStatements(compKeyword != null)
            assert(TokenType.CloseBrace)

            val function = Function(
                classReference,
                accessor,
                mutKeyword,
                compKeyword,
                name,
                parameters,
                returnType,
                statements,
            )

            functions += function
        }

        return functions
    }

    private fun parseParameters(): List<Parameter> {
        val parameters = mutableListOf<Parameter>()

        while (peek()?.type == TokenType.Identifier) {
            var mutableKeyword = next()
            val parameterName: Token?

            if (mutableKeyword?.literal == "mut") {
                parameterName = assert(TokenType.Identifier)
            } else {
                parameterName = mutableKeyword
                mutableKeyword = null
            }

            val colon = assert(TokenType.Colon)
            val type = parseQualifiedName(true)

            parameters += Parameter(
                mutableKeyword,
                parameterName,
                colon,
                type
            )

            if (peek()?.type == TokenType.Comma) {
                consume()
            } else break
        }

        return parameters
    }

    private fun parseStatements(inCompanionContext: Boolean = false): List<Statement> {
        val statements = mutableListOf<Statement>()

        while (pos < tokens.size && peek()?.type != TokenType.CloseBrace) {
            val mutKeyword =
                if (peek()?.isMutKeyword() == true) next()
                else null

            if (peekMultiple(2) == listOf(TokenType.Identifier, TokenType.ColonEqual)) {
                // Variable Declaration
                val name = next()
                val operator = next()
                val expression = parseAssignment(inCompanionContext)

                statements += VariableDeclaration(
                    mutKeyword,
                    name,
                    operator,
                    expression
                )
            } else if (peek()?.isReturnKeyword() == true) {
                consume()

                statements += ReturnStatement(parseExpression(inCompanionContext, true))
            } else statements += ExpressionStatement(parseExpression(inCompanionContext))
        }

        return statements
    }

    private fun parseExpression(inCompanionContext: Boolean, retainValue: Boolean = false): Expression? =
        parseAssignment(inCompanionContext, retainValue)

    private fun parseAssignment(inCompanionContext: Boolean, retainValue: Boolean = false): Expression? {
        var expression = if (peekMultiple(2) == listOf(TokenType.Identifier, TokenType.Equal)) {
            val name = next()
            val operator = next()
            val expression = parseExpression(inCompanionContext, retainValue)

            AssignmentExpression(name, operator, expression, retainValue)
        } else {
            parseBinaryExpression(inCompanionContext = inCompanionContext)
        }

        while (peek()?.type == TokenType.Dot) {
            // Chain calling
            consume()

            val name = assert(TokenType.Identifier)

            if (peek()?.type == TokenType.OpenParenthesis) {
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
                val pos = name?.pos?.extend(name.pos)

                expression = IdentifierCallExpression(null, name, previousExpression = expression, pos = pos)
            }
        }

        return expression
    }

    private fun parseBinaryExpression(
        parentPrecedence: Int = 0,
        inCompanionContext: Boolean
    ): Expression? {
        var left: Expression?
        val unaryPrecedence = peek()?.type?.unaryPrecedence() ?: 0

        left = if (unaryPrecedence != 0 && unaryPrecedence >= parentPrecedence) {
            val operator = next()
            val right = parseBinaryExpression(unaryPrecedence, inCompanionContext)

            UnaryExpression(operator, right)
        } else parsePrimaryExpression(inCompanionContext)

        while (true) {
            val binaryPrecedence = peek()?.type?.binaryPrecedence() ?: 0

            if (binaryPrecedence == 0 || binaryPrecedence <= parentPrecedence) break

            val operator = next()
            val right = parseBinaryExpression(binaryPrecedence, inCompanionContext)

            left = BinaryExpression(
                left,
                operator,
                right
            )
        }

        return left
    }

    private fun parsePrimaryExpression(inCompanionContext: Boolean): Expression? =
        when (peek()?.type) {
            TokenType.IntegerLiteral -> IntegerLiteral(next())
            TokenType.FloatLiteral -> FloatLiteral(next())
            TokenType.Identifier -> parseSecondaryExpression(inCompanionContext)
            TokenType.OpenBrace -> parseArrayInitialization(inCompanionContext)
            else -> null
        }

    private fun parseSecondaryExpression(inCompanionContext: Boolean): Expression {
        val name = next()

        return if (peek()?.type == TokenType.OpenParenthesis) {
            // Function Call
            consume()

            val arguments = parseArguments(inCompanionContext)

            assert(TokenType.CloseParenthesis)

            FunctionCallExpression(null, name, arguments, inCompanionContext)
        } else if (peek()?.type == TokenType.DoubleColon) {
            // Companion member calling, e.g. path::Class.field, path::Class.func(...)
            // TODO: This also might be id::class.class
            consume()
            val classPath = parseQualifiedName(prependPath = "${name?.literal}.", startPos = name?.pos)

            if (peek()?.type == TokenType.OpenBrace || peek()?.type == TokenType.OpenBracket) {
                // Process array type of initialization
                while (peek()?.type == TokenType.OpenBracket) {
                    consume()
                    assert(TokenType.CloseBracket)

                    classPath?.path += "[]"
                }

                // Type-inferred array initialization
                val expressions = arrayListOf<Expression?>()
                consume()

                while (peek()?.type != TokenType.CloseBrace) {
                    expressions += parseExpression(inCompanionContext, true)

                    if (peek()?.type == TokenType.Comma) consume()
                    else break
                }

                val closeBrace = assert(TokenType.CloseBrace)

                return ArrayInitialization(classPath, expressions, name?.pos?.extend(closeBrace?.pos))
            }

            assert(TokenType.Dot)

            val memberName = assert(TokenType.Identifier)

            if (peek()?.type == TokenType.OpenParenthesis) {
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
        } else if (peek()?.type == TokenType.OpenBrace || peek()?.type == TokenType.OpenBracket) {
            // Process array type of initialization
            var classPath = name?.literal

            while (peek()?.type == TokenType.OpenBracket) {
                consume()

                if (peek()?.type != TokenType.CloseBracket) {
                    // Array Declaration, e.g. int[10][10]{}
                    // TODO: Support array stream initialization, e.g. int[10][10]{ it => expr }
                    val dimensionExpressions = mutableListOf<Expression?>()

                    while (true) {
                        dimensionExpressions += parseExpression(inCompanionContext, true)

                        val closeBracket = assert(TokenType.CloseBracket)

                        name?.pos?.extend(closeBracket?.pos)

                        if (peek()?.type == TokenType.OpenBracket) consume()
                        else break
                    }

                    assert(TokenType.OpenBrace)
                    val closeBrace = assert(TokenType.CloseBrace)

                    classPath += "[]".repeat(dimensionExpressions.size)

                    return ArrayDeclaration(
                        Reference(classPath ?: "", classPath ?: "", name?.pos),
                        dimensionExpressions,
                        name?.pos?.extend(closeBrace?.pos)
                    )
                }

                val closeBracket = assert(TokenType.CloseBracket)

                name?.pos?.extend(closeBracket?.pos)

                classPath += "[]"
            }

            // Type-inferred array initialization
            // TODO: Support usage
            val expressions = arrayListOf<Expression?>()
            consume()

            while (peek()?.type != TokenType.CloseBrace) {
                expressions += parseExpression(true, inCompanionContext)

                if (peek()?.type == TokenType.Comma) consume()
                else break
            }

            val closeBrace = assert(TokenType.CloseBrace)

            return ArrayInitialization(
                Reference(classPath ?: "", classPath ?: "", name?.pos),
                expressions,
                name?.pos?.extend(closeBrace?.pos)
            )
        } else {
            if (peek()?.type == TokenType.Dot) {
                // Companion member calling, e.g. Class.field, Class.func()
                // TODO: Support usage first
            }
            // Local identifier Call, e.g local variables, local class fields
            IdentifierCallExpression(null, name)
        }
    }

    // parseArrayInitialization parses array initialization that didn't specific its type
    private fun parseArrayInitialization(inCompanionContext: Boolean): Expression {
        val openBrace = next()

        val expressions = mutableListOf<Expression?>()

        while (peek()?.type != TokenType.CloseBrace) {
            expressions += parseExpression(inCompanionContext, true)

            if (peek()?.type == TokenType.Comma) consume()
            else break
        }

        val closeBrace = assert(TokenType.CloseBrace)

        return ArrayInitialization(
            null,
            expressions,
            openBrace?.pos?.extend(closeBrace?.pos)
        )
    }

    private fun parseArguments(inCompanionContext: Boolean): List<Expression?> {
        val arguments = arrayListOf<Expression?>()

        while (peek()?.type != TokenType.CloseParenthesis) {
            arguments += parseExpression(inCompanionContext, true)

            if (peek()?.type == TokenType.Comma) {
                consume()
            } else break
        }

        return arguments
    }
}