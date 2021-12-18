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
        val functions = parseFunctions()
        assert(TokenType.CloseBrace)

        val clazz = Class(accessor, classKeyword, className, functions)

        return File(path, clazz)
    }

    /**
     * Used to parse type names and usage reference
     */
    private fun parseQualifiedName(isParameter: Boolean = false): Reference? {
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

            Reference(nameBuilder, className, startPos)
        }
    }

    private fun parseFunctions(compKeyword: Token? = null): List<Function> {
        var compScopeDeclared = false
        val functions = mutableListOf<Function>()

        while (pos < tokens.size && peek()?.type != TokenType.CloseBrace) { // Break the loop after encountered class declaration's close bracket
            var accessor = assert(TokenType.Identifier)
            var mutKeyword: Token?
            val fnKeyword: Token?

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
                functions += parseFunctions(accessor)
                assert(TokenType.CloseBrace)
                continue
            }

            if (accessor?.isAccessorKeyword() == true) {
                mutKeyword = next()

                if (mutKeyword?.isMutKeyword() == true) {
                    fnKeyword = next()

                    if (fnKeyword?.isFnKeyword() != true) {
                        reports.reportExpectedFnDeclaration(peek(-1))
                    }
                } else if (mutKeyword?.isFnKeyword() == true) {
                    fnKeyword = mutKeyword
                    mutKeyword = null
                } else {
                    reports.reportExpectedFnDeclaration(peek(-1))

                    fnKeyword = null
                }
            } else if (accessor?.isMutKeyword() == true) {
                mutKeyword = accessor
                accessor = null
                fnKeyword = assert(TokenType.Identifier)

                if (fnKeyword?.isFnKeyword() != true) {
                    reports.reportExpectedFnDeclaration(peek(-1))
                }
            } else if (accessor?.isFnKeyword() == true) {
                fnKeyword = accessor
                accessor = null
                mutKeyword = null
            } else {
                reports.reportExpectedFnDeclaration(peek(-1))

                mutKeyword = null
                fnKeyword = null
            }

            val functionName = assert(TokenType.Identifier)
            val openParenthesis = assert(TokenType.OpenParenthesis)
            val parameters = parseParameters()
            val closeParenthesis = assert(TokenType.CloseParenthesis)
            val colon: Token?
            val returnType: Reference?

            if (peek()?.type == TokenType.Colon) {
                colon = next()
                returnType = parseQualifiedName(true)
            } else {
                colon = null
                returnType = null
            }

            val openBracket = assert(TokenType.OpenBrace)
            val statements = parseStatements()
            val closeBracket = assert(TokenType.CloseBrace)

            val function = Function(
                accessor,
                mutKeyword,
                compKeyword,
                fnKeyword,
                functionName,
                openParenthesis,
                parameters, closeParenthesis,
                colon,
                returnType,
                openBracket,
                statements,
                closeBracket
            )

            functions += function
        }

        return functions
    }

    private fun parseParameters(): List<Parameter> {
        val parameters = mutableListOf<Parameter>()
        var hasNext = true

        while (pos < tokens.size && hasNext) {
            hasNext = false
            if (peek()?.type == TokenType.Identifier) {
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
                    hasNext = true
                }
            } else break
        }

        return parameters
    }

    private fun parseStatements(): List<Statement> {
        val statements = mutableListOf<Statement>()

        while (pos < tokens.size && peek()?.type != TokenType.CloseBrace) {
            val mutKeyword =
                if (peek()?.isMutKeyword() == true) next()
                else null

            if (peekMultiple(2) == listOf(TokenType.Identifier, TokenType.ColonEqual)) {
                // Variable Declaration
                val name = next()
                val operator = next()
                val expression = parseAssignment()

                statements += VariableDeclaration(
                    mutKeyword,
                    name,
                    operator,
                    expression
                )
            } else statements += ExpressionStatement(parseAssignment())
        }

        return statements
    }

    private fun parseAssignment(): Expression? =
        if (peekMultiple(2) == listOf(TokenType.Identifier, TokenType.Equal)) {
            val name = next()
            val operator = next()
            val expression = parseAssignment()

            AssignmentExpression(name, operator, expression)
        } else parseBinaryExpression()

    private fun parseBinaryExpression(parentPrecedence: Int = 0): Expression? {
        var left: Expression?
        val unaryPrecedence = peek()?.type?.unaryPrecedence() ?: 0

        left = if (unaryPrecedence != 0 && unaryPrecedence >= parentPrecedence) {
            val operator = next()
            val right = parseBinaryExpression(unaryPrecedence)

            UnaryExpression(operator, right)
        } else parseExpression()

        while (true) {
            val binaryPrecedence = peek()?.type?.binaryPrecedence() ?: 0

            if (binaryPrecedence == 0 || binaryPrecedence >= parentPrecedence) break

            val operator = next()
            val right = parseBinaryExpression(binaryPrecedence)

            left = BinaryExpression(
                left,
                operator,
                right
            )
        }

        return left
    }

    private fun parseExpression(): Expression? =
        when (peek()?.type) {
            TokenType.IntegerLiteral -> IntegerLiteral(next())
            TokenType.FloatLiteral -> FloatLiteral(next())
            TokenType.Identifier -> parseIdentifierOrFunctionCall()
            else -> null
        }

    // TODO: Support FunctionCall
    private fun parseIdentifierOrFunctionCall(): Expression? {
        return IdentifierExpression(next())
    }
}