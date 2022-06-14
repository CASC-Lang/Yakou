package org.yakou.lang

import org.yakou.lang.cli.ArgumentProcessor
import org.yakou.lang.lexer.Lexer
import java.io.File

fun main(args: Array<String>) {
    val preference = ArgumentProcessor().processPreference()

    val tokens = Lexer(File(Lexer::class.java.getResource("/test.yk").file)).lex()
}