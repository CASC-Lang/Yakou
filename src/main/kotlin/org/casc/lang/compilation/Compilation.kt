package org.casc.lang.compilation

import org.casc.lang.checker.Checker
import org.casc.lang.emitter.Emitter
import org.casc.lang.lexer.Lexer
import org.casc.lang.parser.Parser
import java.io.File

class Compilation(val file: File) {
    fun compile() {
        if (file.isDirectory) {
            // TODO: Implement Directory Compilation WIP
//            val source = file.readText() // TODO: Change to readLines
//            val lexResult = Lexer(source)
//            val parseResult = null
        } else if (file.isFile) {
            // Init Preference
            Preference.outputDir = file.parentFile

            val filePath = "./${file.name}"
            // Compilation
            val source = file.readLines()

            // Unit I: Lexer
            /**
             * Known as lexical analyzer, handles source text parsing into token form.
             */
            val (lexReports, lexResult) = Lexer(source).lex()

            lexReports.forEach { it.printReport(filePath, source) }

            if (lexReports.filterIsInstance<Error>().isNotEmpty()) return

            // Unit II: Parser
            /**
             * Parse tokens into an Abstract Syntax Tree (aka AST) for further unit to use with,
             * parser also asserts that the certainty of source code validity.
             */
            val (parseReports, parseResult) = Parser(arrayOf(file.absolutePath to lexResult)).parse()

            parseReports.forEach { it.printReport(filePath, source) }

            if (parseReports.filterIsInstance<Error>().isNotEmpty()) return

            // Unit III: Checker
            /**
             * Checks complex syntax validity and variables' type.
             */
            val (checkReports, checkResult) = Checker().check(parseResult)

            checkReports.forEach { it.printReport(filePath, source) }

            if (checkReports.filterIsInstance<Error>().isNotEmpty()) return

            // Unit IV: Emitter
            /**
             * Emits AST into backend languages, like JVM bytecode.
             * only JVM backend is available at this moment.
             */
            Emitter(file.parentFile, checkResult).emit()
        }
    }
}