package io.github.chaosunity.casc.compilation

import io.github.chaosunity.casc.CASCLexer
import io.github.chaosunity.casc.CASCParser
import io.github.chaosunity.casc.parsing.CompilationUnit
import io.github.chaosunity.casc.visitor.CompilationUnitVisitor
import org.antlr.v4.kotlinruntime.CharStreams
import org.antlr.v4.kotlinruntime.CommonTokenStream

open class Parser {
    fun getCompilationUnit(absolutePath: String): CompilationUnit {
        val charStream = CharStreams.fromFileName(absolutePath, Charsets.UTF_8)
        val lexer = CASCLexer(charStream)
        val tokenStream = CommonTokenStream(lexer)
        val parser = CASCParser(tokenStream)
        val compilationUnitVisitor = CompilationUnitVisitor()

        return parser.compilationUnit().accept(compilationUnitVisitor)
    }
}