package org.casc.lang.compilation

import org.casc.lang.ast.File
import org.casc.lang.checker.Checker
import org.casc.lang.emitter.Emitter
import org.casc.lang.lexer.Lexer
import org.casc.lang.parser.Parser
import java.io.BufferedReader
import java.net.URLClassLoader
import java.io.File as JFile
import java.util.*
import kotlin.system.exitProcess

class Compilation(val file: JFile, private val preference: AbstractPreference = GlobalPreference) {
    companion object {
        private var queuedFiles: MutableList<Triple<Boolean, String, List<String>>> = mutableListOf()
        private var parsedResults: MutableList<File> = mutableListOf()
        private var progressingCompilations: Stack<String> = Stack()
        private lateinit var lexer: Lexer
        private lateinit var parser: Parser
        private lateinit var checker: Checker
        private lateinit var emitter: Emitter

        fun compileClass(preference: AbstractPreference, classPath: String): Boolean {
            val result =
                queuedFiles.withIndex()
                    .find { (_, it) -> it.second.split('\\', '/', '.').dropLast(1).joinToString(".") == classPath }

            return if (result != null) {
                val (index, fileResult) = result
                val (compiled, filePath, source) = fileResult

                if (compiled) return true

                if (progressingCompilations.contains(filePath)) {
                    Error("Circular compilation detected, compilation terminated.").printReport(filePath, listOf())
                    println("Dependency tree:")

                    val startIndex = progressingCompilations.indexOf(filePath)

                    for (i in startIndex until progressingCompilations.size) {
                        if (startIndex == i) {
                            print("starts from\t| ")
                        } else if (progressingCompilations.lastIndex == i) {
                            print("occurs here\t| ")
                        } else {
                            print("      |    \t| ")
                        }

                        println(progressingCompilations[i])
                    }

                    exitProcess(-1)
                }

                progressingCompilations += filePath
                // Unit I and Unit II were already progressed in main

                // Unit III: Checker
                /**
                 * Checks complex syntax validity and variables' type.
                 */
                val (checkReports, checkResult) = checker.check(parsedResults[index])

                checkReports.printReports(filePath, source)

                if (checkReports.hasError()) return false

                // Unit IV: Emitter
                /**
                 * Emits AST into backend languages, like JVM bytecode.
                 * only JVM backend is available at this moment.
                 */
                emitter.emit(JFile(preference.outputDir, JFile(filePath).parentFile?.path ?: ""), checkResult)

                // Reload class loader so JVM can load it
                preference.classLoader = URLClassLoader.newInstance(arrayOf(preference.outputDir.toURI().toURL()))

                // Marks file as compiled
                queuedFiles[index] = fileResult.copy(true)

                progressingCompilations.pop()

                true
            } else {
                false
            }
        }

        private fun List<Report>.printReports(fileName: String, source: List<String>) =
            this.forEach { it.printReport(fileName, source) }

        private fun List<Report>.hasError(): Boolean =
            this.filterIsInstance<Error>().isNotEmpty()
    }

    init {
        lexer = Lexer(preference)
        parser = Parser(preference)
        checker = Checker(preference)
        emitter = Emitter(preference)
    }

    fun compile() {
        if (file.isDirectory) {
            preference.outputDir = JFile(file.parentFile.path, "/out")
            preference.outputDir.mkdir()

            // Compilations
            for (sourceFile in file.walk())
                if (sourceFile.isFile && sourceFile.extension == "casc")
                    queuedFiles += Triple(false, sourceFile.toRelativeString(file), sourceFile.readLines())

            val removeIndex = mutableListOf<Int>()

            for (i in 0 until queuedFiles.size) {
                val (_, filePath, source) = queuedFiles[i]

                // Unit I: Lexer
                /**
                 * Known as lexical analyzer, handles source text parsing into token form.
                 */
                val (lexReports, lexResult) = lexer.lex(source)

                lexReports.printReports(filePath, source)

                if (lexReports.hasError()) {
                    removeIndex += i
                    continue
                }

                // Unit II: Parser
                /**
                 * Parse tokens into an Abstract Syntax Tree (aka AST) for further unit to use with,
                 * parser also asserts that the certainty of source code validity.
                 */
                val (parseReports, parseResult) = parser.parse(filePath, lexResult)

                parseReports.printReports(filePath, source)

                if (parseReports.hasError()) {
                    removeIndex += i
                    continue
                }

                parsedResults += parseResult
            }

            for (i in removeIndex) queuedFiles.removeAt(i)

            for (i in 0 until queuedFiles.size) {
                val (compiled, filePath, source) = queuedFiles[i]

                if (!compiled) {
                    progressingCompilations += filePath

                    // Unit III: Checker
                    /**
                     * Checks complex syntax validity and variables' type.
                     */
                    val (checkReports, checkResult) = checker.check(parsedResults[i])

                    checkReports.printReports(filePath, source)

                    if (checkReports.hasError()) continue

                    // Unit IV: Emitter
                    /**
                     * Emits AST into backend languages, like JVM bytecode.
                     * only JVM backend is available at this moment.
                     */
                    emitter.emit(JFile(preference.outputDir, JFile(filePath).parentFile?.path ?: ""), checkResult)

                    queuedFiles[i] = queuedFiles[i].copy(true)

                    progressingCompilations.pop()
                }
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
            val (parseReports, parseResult) = parser.parse(file.absolutePath, lexResult)

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