package io.github.chaosunity.casc.parse

import io.github.chaosunity.antlr.CASCLexer
import io.github.chaosunity.antlr.CASCParser
import io.github.chaosunity.casc.bytecodegen.instructions.Instruction
import org.antlr.v4.runtime.ANTLRFileStream
import org.antlr.v4.runtime.CommonTokenStream

class SyntaxTreeTraverser {
    fun getInstructions(fileAbsolutePath: String): ArrayDeque<Instruction> {
        val fileStream = ANTLRFileStream(fileAbsolutePath, "utf-8")
        val lexer = CASCLexer(fileStream)
        val tokenStream = CommonTokenStream(lexer)
        val parser = CASCParser(tokenStream)
        val listener = CASCTreeWalkListener()

        parser.addParseListener(listener)
        parser.compilationUnit()

        return listener.instructionsQueue
    }
}