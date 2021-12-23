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

            val returnType = if (peek()?.type == TokenType.Colon) {
                consume()
                parseQualifiedName(true)
            } else null

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

        while (pos < tokens.size && peek()?.type != TokenType.CloseBrace)
            statements += parseStatement(inCompanionContext)

        return statements
    }

    private fun parseStatement(inCompanionContext: Boolean = false): Statement {
        val mutKeyword =
            if (peek()?.isMutKeyword() == true) next()
            else null

        return if (peekMultiple(2) == listOf(TokenType.Identifier, TokenType.ColonEqual)) {
            // Variable declaration
            val name = next()
            val operator = next()
            val expression = parseAssignment(inCompanionContext)

            VariableDeclaration(
                mutKeyword,
                name,
                operator,
                expression
            )
        } else if (peek()?.isReturnKeyword() == true) {
            // Return statement
            val returnKeyword = next()
            val expression = parseExpression(inCompanionContext, true)

            ReturnStatement(expression, pos = returnKeyword?.pos?.extend(expression?.pos))
        } else if (peek()?.isIfKeyword() == true) {
            // if-else statement
            val ifKeyword = next()
            val condition = parseExpression(inCompanionContext, true)

            val trueStatement = parseStatement(inCompanionContext)

            val elseStatement = if (peek()?.isElseKeyword() == true) {
                consume()
                parseStatement(inCompanionContext)
            } else null

            IfStatement(
                condition,
                trueStatement,
                elseStatement,
                ifKeyword?.pos?.extend(trueStatement.pos)
            )
        } else if (peek()?.type == TokenType.OpenBrace) {
            // Block statement
            val openBrace = next()
            val statements = parseStatements(inCompanionContext)
            val closeBrace = assert(TokenType.CloseBrace)

            BlockStatement(
                statements,
                openBrace?.pos?.extend(closeBrace?.pos)
            )
        } else ExpressionStatement(parseExpression(inCompanionContext))
    }

    private fun parseExpression(inCompanionContext: Boolean, retainValue: Boolean = false): Expression? =
        parseAssignment(inCompanionContext, retainValue)

    private fun parseAssignment(inCompanionContext: Boolean, retainValue: Boolean = false): Expression? {
        var expression = parseBinaryExpression(0, inCompanionContext)

        while (peek()?.type == TokenType.Equal) {
            // Assignment
            val operator = next()

            val rightExpression = parseExpression(inCompanionContext, true)

            expression = AssignmentExpression(expression, operator, rightExpression, retainValue, )
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

    private fun parsePrimaryExpression(inCompanionContext: Boolean): Expression? {
        var expression = when (peek()?.type) {
            TokenType.IntegerLiteral -> IntegerLiteral(next())
            TokenType.FloatLiteral -> FloatLiteral(next())
            TokenType.CharLiteral -> CharLiteral(next())
            TokenType.StringLiteral -> StrLiteral(next())
            TokenType.Identifier -> when (peek()?.literal) {
                "true", "false" -> BoolLiteral(next())
                "null" -> NullLiteral(next())
                else -> parseSecondaryExpression(inCompanionContext)
            }
            TokenType.OpenBrace -> parseArrayInitialization(inCompanionContext)
            else -> null
        }

        if (expression is IdentifierCallExpression || expression is FunctionCallExpression) {
            while (true) {
                if (peek()?.type == TokenType.Dot) {
                    // Chain calling
                    consume()

                    val name = assert(TokenType.Identifier)

                    if (peek()?.type == TokenType.OpenParenthesis) {
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
                } else if (peek()?.type == TokenType.OpenBracket) {
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

            if (peek()?.type == TokenType.Colon) return parseArrayInitialization(inCompanionContext, classPath)

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
        } else if (peek()?.type == TokenType.Colon) parseArrayInitialization(inCompanionContext, Reference(name))
        else {
            if (peek()?.type == TokenType.Dot) {
                // Companion member calling, e.g. Class.field, Class.func()
                // TODO: Support usage first
            }
            // Local identifier Call, e.g local variables, local class fields
            IdentifierCallExpression(null, name)
        }
    }

    // parseArrayInitialization parses array initialization & declaration
    private fun parseArrayInitialization(inCompanionContext: Boolean, typeReference: Reference? = null): Expression {
        return if (peek()?.type == TokenType.Colon && typeReference != null) {
            // Array declaration (`int:[10]{}`) or Array Initialization (`int:[]{...}`)
            consume()

            val dimensionElements = mutableListOf<Expression?>()

            while (peek()?.type != TokenType.OpenBrace) {
                consume()

                if (peek()?.type != TokenType.CloseBracket) {
                    // Array declaration
                    dimensionElements += parseExpression(inCompanionContext, true)
                }

                val closeBracket = assert(TokenType.CloseBracket)

                typeReference.path += "[]"

                if (dimensionElements.isEmpty()) {
                    typeReference.position?.extend(closeBracket?.pos)
                }
            }

            assert(TokenType.OpenBrace)

            if (dimensionElements.isEmpty()) {
                // Array initialization
                val elements = mutableListOf<Expression?>()

                while (peek()?.type != TokenType.CloseBrace) {
                    elements += parseExpression(inCompanionContext, true)

                    if (peek()?.type == TokenType.Comma) consume()
                    else break
                }

                val closeBrace = assert(TokenType.CloseBrace)

                ArrayInitialization(
                    typeReference,
                    elements,
                    typeReference.position?.copy()?.extend(closeBrace?.pos)
                )
            } else {
                // Array Declaration
                val closeBrace = assert(TokenType.CloseBrace)

                ArrayDeclaration(
                    typeReference,
                    dimensionElements,
                    typeReference.position?.copy()?.extend(closeBrace?.pos)
                )
            }
        } else {
            val openBrace = assert(TokenType.OpenBrace)

            val expressions = mutableListOf<Expression?>()

            while (peek()?.type != TokenType.CloseBrace) {
                expressions += parseExpression(inCompanionContext, true)

                if (peek()?.type == TokenType.Comma) consume()
                else break
            }

            val closeBrace = assert(TokenType.CloseBrace)

            ArrayInitialization(
                null,
                expressions,
                openBrace?.pos?.extend(closeBrace?.pos)
            )
        }
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