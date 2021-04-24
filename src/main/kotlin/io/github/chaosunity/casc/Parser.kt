package io.github.chaosunity.casc

import io.github.chaosunity.antlr.CASCLexer
import io.github.chaosunity.antlr.CASCParser
import io.github.chaosunity.casc.antlr.CASCTreeWalkErrorListener
import io.github.chaosunity.casc.parsing.global.CompilationUnit
import io.github.chaosunity.casc.visitor.CompilationUnitVisitor
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream

class Parser {
    fun getCompilationUnit(absolutePath: String): CompilationUnit {
        val charStream = CharStreams.fromFileName(absolutePath, Charsets.UTF_8)
        val lexer = CASCLexer(charStream)
        val tokenStream = CommonTokenStream(lexer)
        val parser = CASCParser(tokenStream)

        val errorListener = CASCTreeWalkErrorListener()
        parser.addErrorListener(errorListener)

        val compilationUnitVisitor = CompilationUnitVisitor()
        return parser.compilationUnit().accept(compilationUnitVisitor)
    }
}