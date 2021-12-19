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
            if (className != null) Reference(className.literal, className.literal) // TODO: Add package path to path
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
    private fun parseQualifiedName(isParameter: Boolean = false, prependPath: String = ""): Reference? {
        var startPos: Position? = null
        var nameBuilder = ""

        while (pos < tokens.size && peek()?.type == TokenType.Identifier) {
            val identifier = assert(TokenType.Identifier)!!

            if (startPos == null) startPos = identifier.pos

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

            Reference(prependPath + nameBuilder, className, startPos)
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
            val colon: Token?
            val returnType: Reference?

            if (peek()?.type == TokenType.Colon) {
                colon = next()
                returnType = parseQualifiedName(true)
            } else {
                colon = null
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
            } else statements += ExpressionStatement(parseExpression(inCompanionContext = inCompanionContext))
        }

        return statements
    }

    private fun parseExpression(retainValue: Boolean = false, inCompanionContext: Boolean = false): Expression? =
        parseAssignment(retainValue, inCompanionContext)

    private fun parseAssignment(retainValue: Boolean = false, inCompanionContext: Boolean = false): Expression? {
        var expression = if (peekMultiple(2) == listOf(TokenType.Identifier, TokenType.Equal)) {
            val name = next()
            val operator = next()
            val expression = parseExpression(retainValue, inCompanionContext)

            AssignmentExpression(name, operator, expression, retainValue)
        } else {
            parseBinaryExpression(retainValue = retainValue, inCompanionContext = inCompanionContext)
        }

        while (peek()?.type == TokenType.Dot) {
            // Chain calling
            consume()

            val name = assert(TokenType.Identifier)

            if (peek()?.type == TokenType.OpenParenthesis) {
                consume()

                val arguments = parseArguments()

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
        retainValue: Boolean = false,
        inCompanionContext: Boolean = false
    ): Expression? {
        var left: Expression?
        val unaryPrecedence = peek()?.type?.unaryPrecedence() ?: 0

        left = if (unaryPrecedence != 0 && unaryPrecedence >= parentPrecedence) {
            val operator = next()
            val right = parseBinaryExpression(unaryPrecedence, retainValue, inCompanionContext)

            UnaryExpression(operator, right)
        } else parsePrimaryExpression(retainValue, inCompanionContext)

        while (true) {
            val binaryPrecedence = peek()?.type?.binaryPrecedence() ?: 0

            if (binaryPrecedence == 0 || binaryPrecedence <= parentPrecedence) break

            val operator = next()
            val right = parseBinaryExpression(binaryPrecedence, retainValue, inCompanionContext)

            left = BinaryExpression(
                left,
                operator,
                right
            )
        }

        return left
    }

    private fun parsePrimaryExpression(retainValue: Boolean = false, inCompanionContext: Boolean = false): Expression? =
        when (peek()?.type) {
            TokenType.IntegerLiteral -> IntegerLiteral(next())
            TokenType.FloatLiteral -> FloatLiteral(next())
            TokenType.Identifier -> parseIdentifierOrFunctionCall(inCompanionContext)
            else -> null
        }

    private fun parseIdentifierOrFunctionCall(inCompanionContext: Boolean): Expression {
        val name = next()

        return if (peek()?.type == TokenType.OpenParenthesis) {
            // Function Call
            consume()

            val arguments = parseArguments()

            assert(TokenType.CloseParenthesis)

            FunctionCallExpression(null, name, arguments, inCompanionContext)
        } else if (peek()?.type == TokenType.DoubleColon) {
            // Companion member calling, e.g. path::Class.field, path::Class.func(...)
            // TODO: This also might be id::class.class
            consume()
            val classPath = parseQualifiedName(prependPath = "${name?.literal}.")

            assert(TokenType.Dot)

            val memberName = assert(TokenType.Identifier)

            if (peek()?.type == TokenType.OpenParenthesis) {
                // Companion function calling
                consume()

                val arguments = parseArguments()

                assert(TokenType.CloseParenthesis)

                val pos = name?.pos?.extend(arguments.lastOrNull()?.pos)?.extend()

                FunctionCallExpression(classPath, memberName, arguments, inCompanionContext, pos = pos)
            } else {
                // Companion field calling
                val pos = name?.pos?.extend(memberName?.pos)

                IdentifierCallExpression(classPath, memberName, pos = pos)
            }
        } else {
            if (peek()?.type == TokenType.Dot) {
                // Companion member calling, e.g. Class.field, Class.func()
            }
            // Local identifier Call, e.g local variables, local class fields
            IdentifierCallExpression(null, name)
        }
    }

    private fun parseArguments(): List<Expression?> {
        val arguments = arrayListOf<Expression?>()

        while (peek()?.type != TokenType.CloseParenthesis) {
            arguments += parseExpression(true)

            if (peek()?.type == TokenType.Comma) {
                consume()
            } else break
        }

        return arguments
    }
}