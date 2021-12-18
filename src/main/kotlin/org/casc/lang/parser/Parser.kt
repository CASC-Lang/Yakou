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

    private fun assert(type: Token.TokenType): Token? = when {
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

    private fun last(): Token? =
        peek(-1)

    private fun next(): Token? = when {
        tokens.isEmpty() -> null
        pos >= tokens.size -> null
        else -> tokens[pos++]
    }

    private fun parseFile(path: String): File {
        // TODO: Package
        // TODO: Usages
        // Parse class declaration
        var accessor = assert(Token.TokenType.Identifier)
        val classKeyword: Token?

        if (accessor?.isClassKeyword() == true) { // If class has no modifier, then we move accessor token to class token
            classKeyword = accessor
            accessor = null
        } else {
            classKeyword = assert(Token.TokenType.Identifier)

            if (classKeyword?.isClassKeyword() != true) {
                reports += Error(
                    last()!!.pos,
                    "Expected class declaration"
                )
            }
        }

        val className = assert(Token.TokenType.Identifier)
        assert(Token.TokenType.OpenBracket)
        // TODO: Parse fields
        assert(Token.TokenType.CloseBracket)

        // Parse major implementation
        val implKeyword = assert(Token.TokenType.Identifier)

        if (implKeyword?.isImplKeyword() != true) {
            reports += Error(
                last()!!.pos,
                "Expected implementations for class"
            )
        }

        val implName = assert(Token.TokenType.Identifier)

        if (implName?.literal != className?.literal) {
            reports += Error(
                last()!!.pos,
                "Unexpected implementation for class ${implName?.literal}"
            )
        }

        assert(Token.TokenType.OpenBracket)
        val functions = parseFunctions()
        assert(Token.TokenType.CloseBracket)

        val clazz = Class(accessor, classKeyword, className, functions)

        return File(path, clazz)
    }

    /**
     * Used to parse type names and usage reference
     */
    private fun parseQualifiedName(isParameter: Boolean = false): Reference? {
        var startPos: Position? = null
        var nameBuilder = ""

        while (pos < tokens.size && peek()?.type == Token.TokenType.Identifier) {
            val identifier = assert(Token.TokenType.Identifier)!!

            if (startPos == null) startPos = identifier.pos

            nameBuilder += identifier.literal

            if (peek()?.type == Token.TokenType.DoubleColon) {
                assert(Token.TokenType.DoubleColon)
                nameBuilder += "."
            } else break
        }

        if (isParameter) {
            while (pos < tokens.size && peek()?.type == Token.TokenType.OpenBrace) {
                assert(Token.TokenType.OpenBrace)
                assert(Token.TokenType.CloseBrace) ?: break

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

        while (pos < tokens.size && peek()?.type != Token.TokenType.CloseBracket) { // Break the loop after encountered class declaration's close bracket
            var accessor = assert(Token.TokenType.Identifier)
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
                assert(Token.TokenType.OpenBracket)
                functions += parseFunctions(accessor)
                assert(Token.TokenType.CloseBracket)
                continue
            }

            if (accessor?.isAccessorKeyword() == true) {
                mutKeyword = assert(Token.TokenType.Identifier)

                if (mutKeyword?.isMutKeyword() == true) {
                    fnKeyword = assert(Token.TokenType.Identifier)

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
                fnKeyword = assert(Token.TokenType.Identifier)

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

            val functionName = assert(Token.TokenType.Identifier)
            val openParenthesis = assert(Token.TokenType.OpenParenthesis)
            val parameters = parseParameters()
            val closeParenthesis = assert(Token.TokenType.CloseParenthesis)
            val colon: Token?
            val returnType: Reference?

            if (peek()?.type == Token.TokenType.Colon) {
                colon = assert(Token.TokenType.Colon)
                returnType = parseQualifiedName(true)
            } else {
                colon = null
                returnType = null
            }

            val openBracket = assert(Token.TokenType.OpenBracket)
            val closeBracket = assert(Token.TokenType.CloseBracket)

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
            if (peek()?.type == Token.TokenType.Identifier) {
                var mutableKeyword = assert(Token.TokenType.Identifier)
                val parameterName: Token?

                if (mutableKeyword?.literal == "mut") {
                    parameterName = assert(Token.TokenType.Identifier)
                } else {
                    parameterName = mutableKeyword
                    mutableKeyword = null
                }

                val colon = assert(Token.TokenType.Colon)
                val type = parseQualifiedName(true)

                parameters += Parameter(
                    mutableKeyword,
                    parameterName,
                    colon,
                    type
                )

                if (peek()?.type == Token.TokenType.Comma) {
                    assert(Token.TokenType.Comma)
                    hasNext = true
                }
            } else break
        }

        return parameters
    }
}