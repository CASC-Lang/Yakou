package org.casc.lang.compilation

import kotlinx.coroutines.*
import org.casc.lang.ast.File
import org.casc.lang.checker.Checker
import org.casc.lang.emitter.Emitter
import org.casc.lang.lexer.Lexer
import org.casc.lang.parser.Parser
import org.casc.lang.table.Table
import org.casc.lang.utils.pforeach
import org.casc.lang.utils.pmap
import java.io.BufferedReader
import java.net.URLClassLoader
import java.util.*
import kotlin.streams.asStream
import kotlin.system.exitProcess
import java.io.File as JFile

class Compilation(
    val file: JFile,
    private val preference: AbstractPreference = GlobalPreference,
    private val lexer: Lexer = Lexer(preference),
    private val parser: Parser = Parser(preference),
    private val checker: Checker = Checker(preference),
    private val emitter: Emitter = Emitter(preference)
) {
    companion object {
        private fun List<Report>.printReports(fileName: String, source: List<String>) =
            this.forEach { it.printReport(fileName, source) }

        private fun List<Report>.hasError(): Boolean =
            this.filterIsInstance<Error>().isNotEmpty()
    }

    fun compile() {
        if (file.isDirectory) {
            val sources = mutableMapOf<String, List<String>>()
            val parsedResults = mutableListOf<File>()

            preference.outputDir = JFile(file.parentFile.path, "/out")
            preference.outputDir.mkdir()

            // Compilations
            file.walk().filter { it.isFile && it.extension == "casc" }.toList().pmap pmap@{
                val source = it.readLines()
                val filePath = it.toRelativeString(file)
                sources[filePath] = source

                // Unit I: Lexer
                /**
                 * Known as lexical analyzer, handles source text parsing into token form.
                 */
                val (lexReports, lexResult) = lexer.lex(it.readLines())

                lexReports.printReports(filePath, source)

                if (lexReports.hasError())
                    return@pmap

                // Unit II: Parser
                /**
                 * Parse tokens into an Abstract Syntax Tree (aka AST) for further unit to use with,
                 * parser also asserts that the certainty of source code validity.
                 */
                val (parseReports, parseResult) = parser.parse(filePath, filePath, lexResult)

                parseReports.printReports(filePath, source)

                if (parseReports.hasError())
                    return@pmap

                parsedResults += parseResult
            }

            parsedResults.pforeach {
                Table.cachedClasses += it.clazz.getFullPath() to it
            }

            parsedResults.pforeach pforeach@{
                // Unit III: Checker
                /**
                 * Checks complex syntax validity and variables' type.
                 */
                val (checkReports, checkResult) = checker.check(it)

                checkReports.printReports(it.relativeFilePath, sources[it.relativeFilePath]!!)

                if (checkReports.hasError()) return@pforeach

                // Unit IV: Emitter
                /**
                 * Emits AST into backend languages, like JVM bytecode.
                 * only JVM backend is available at this moment.
                 */
                emitter.emit(JFile(preference.outputDir, JFile(it.path).parentFile?.path ?: ""), checkResult)
            }
        } else if (file.isFile) {
            // Init preferenceerence
            preference.outputDir = file.parentFile

            val outputFileName = "./${file.name}"
            // Compilation
            val source = file.readLines()

            // Unit I: Lexer
            /**
             * Known as lexical analyzer, handles source text parsing into token form.
             */
            val (lexReports, lexResult) = lexer.lex(source)

            lexReports.printReports(outputFileName, source)

            if (lexReports.hasError()) return

            // Unit II: Parser
            /**
             * Parse tokens into an Abstract Syntax Tree (aka AST) for further unit to use with,
             * parser also asserts that the certainty of source code validity.
             */
            val (parseReports, parseResult) = parser.parse(file.absolutePath, file.toRelativeString(preference.outputDir), lexResult)

            parseReports.printReports(outputFileName, source)

            if (parseReports.hasError()) return

            // Unit III: Checker
            /**
             * Checks complex syntax validity and variables' type.
             */
            val (checkReports, checkResult) = checker.check(parseResult)

            checkReports.printReports(outputFileName, source)

            if (checkReports.hasError()) return

            // Unit IV: Emitter
            /**
             * Emits AST into backend languages, like JVM bytecode.
             * only JVM backend is available at this moment.
             */
            emitter.emit(file.parentFile, checkResult)

            if (preference.compileAndRun) {
                val isWindows = System.getProperty("os.name").lowercase().startsWith("windows")
                val process = Runtime.getRuntime().exec(
                    if (isWindows) "cmd.exe /c cd ${file.parentFile.absolutePath} && java ${file.nameWithoutExtension}"
                    else "sh -c cd ${file.parentFile.absolutePath} && java ${file.nameWithoutExtension}"
                )

                BufferedReader(process.inputReader())
                    .lines()
                    .forEach(::println)

                BufferedReader(process.errorReader())
                    .lines()
                    .forEach(::println)
            }
        }
    }
}