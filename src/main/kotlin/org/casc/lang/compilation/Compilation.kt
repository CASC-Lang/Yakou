package org.casc.lang.compilation

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import org.casc.lang.ast.File
import org.casc.lang.ast.Token
import org.casc.lang.checker.Checker
import org.casc.lang.emitter.Emitter
import org.casc.lang.lexer.Lexer
import org.casc.lang.parser.Parser
import org.casc.lang.table.Table
import org.casc.lang.utils.*
import java.io.BufferedReader
import kotlin.time.measureTimedValue
import java.io.File as JFile

class Compilation(
    val file: JFile, private val preference: AbstractPreference = GlobalPreference
) {
    companion object {
        private fun List<Report>.printReports(fileName: String, source: List<String>) =
            this.forEach { it.printReport(fileName, source) }

        private fun List<Report>.hasError(): Boolean = this.filterIsInstance<Error>().isNotEmpty()
    }

    fun compile() {
        if (file.isDirectory) {
            preference.outputDir = JFile(file.parentFile.path, "/out")
            preference.outputDir.mkdir()

            // Compilations
            file.walk().filter { it.isFile && it.extension == "casc" }.toList().map {
                val source = it.readLines()
                val filePath = it.toRelativeString(file)

                // Unit I: Lexer
                /**
                 * Known as lexical analyzer, handles source text parsing into token form.
                 */
                val (lexReports, lexResults) = Lexer(preference).lex(it.readLines())

                lexReports.printReports(filePath, source)

                if (lexReports.hasError()) Quintuple(lexReports, null, it.absolutePath, filePath, source)
                else Quintuple(lexReports, lexResults, it.absolutePath, filePath, source)
            }.mapNotNull {
                it.first.printReports(it.fourth, it.fifth)

                if (it.first.hasError() || it.second == null) null
                else Quadruple(it.second, it.third, it.fourth, it.fifth)
            }.pmap {
                val (lexResult, absoluteFilePath, filePath, source) = it

                // Unit II: Parser
                /**
                 * Parse tokens into an Abstract Syntax Tree (aka AST) for further unit to use with,
                 * parser also asserts that the certainty of source code validity.
                 */
                val (parseReports, parseResult) =
                    Parser(preference).parse(absoluteFilePath, filePath, lexResult)

                parseReports.printReports(filePath, source)

                if (parseReports.hasError()) Quadruple(parseReports, null, filePath, source)
                else Quadruple(parseReports, parseResult, filePath, source)
            }.mapNotNull {
                it.first.printReports(it.third, it.fourth)

                if (it.second == null) null
                else {
                    // Caches class for dummy type checking, used in declaration checking
                    Table.cachedClasses += it.second.clazz.getReference() to it.second

                    Triple(it.second, it.third, it.fourth)
                }
            }.pmap {
                val (file, filePath, source) = it

                // Unit III: Checker
                /**
                 * Checks complex syntax validity and variables' type.
                 *
                 * Phase I:
                 * Check all AST declaration's validity
                 */
                val checker = Checker(preference)
                val (declarationCheckingReports, checkedFile, classScope) = checker.checkDeclaration(file)

                Quintuple(checkedFile, classScope, declarationCheckingReports, filePath, source)
            }.call(Table.cachedClasses::clear).mapNotNull {
                it.third.printReports(it.fourth, it.fifth)

                if (it.third.hasError()) null
                else {
                    Table.cachedClasses += it.first.clazz.getReference() to it.first

                    Quadruple(it.first, it.second, it.fourth, it.fifth)
                }
            }.pmap {
                val (file, scope, filePath, source) = it

                /**
                 * Phase II:
                 * Check all AST declaration's body's validity
                 */
                val checker = Checker(preference)
                val (checkReports, checkResult) = checker.check(file, scope)

                Quadruple(checkReports, if (checkReports.hasError()) null else checkResult, filePath, source)
            }.mapNotNull {
                it.first.printReports(it.third, it.fourth)

                if (it.first.hasError() || it.second == null) null
                else Triple(it.second, it.third, it.fourth)
            }.pmap {
                val (file, _, _) = it

                // Unit IV: Emitter
                /**
                 * Emits AST into backend languages, like JVM bytecode.
                 * only JVM backend is available at this moment.
                 */
                Emitter(preference).emit(
                    JFile(preference.outputDir, JFile(file.relativeFilePath)?.parentFile?.path ?: ""),
                    file
                )
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
            val (lexReports, lexResult) = Lexer(preference).lex(source)

            lexReports.printReports(outputFileName, source)

            if (lexReports.hasError()) return

            // Unit II: Parser
            /**
             * Parse tokens into an Abstract Syntax Tree (aka AST) for further unit to use with,
             * parser also asserts that the certainty of source code validity.
             */
            val (parseReports, parseResult) = Parser(preference).parse(
                file.absolutePath, file.toRelativeString(preference.outputDir), lexResult
            )

            parseReports.printReports(outputFileName, source)

            if (parseReports.hasError()) return

            // Unit III: Checker
            /**
             * Checks complex syntax validity and variables' type.
             */
            val checker = Checker(preference)
            val (declarationReports, declarationCheckedFile, scope) = checker.checkDeclaration(parseResult)

            declarationReports.printReports(outputFileName, source)

            if (declarationReports.hasError()) return

            val (checkReports, checkResult) = checker.check(declarationCheckedFile, scope)

            checkReports.printReports(outputFileName, source)

            if (checkReports.hasError()) return

            // Unit IV: Emitter
            /**
             * Emits AST into backend languages, like JVM bytecode.
             * only JVM backend is available at this moment.
             */
            Emitter(preference).emit(file.parentFile, checkResult)

            if (preference.compileAndRun) {
                val isWindows = System.getProperty("os.name").lowercase().startsWith("windows")
                val process = Runtime.getRuntime().exec(
                    if (isWindows) "cmd.exe /c cd ${file.parentFile.absolutePath} && java ${file.nameWithoutExtension}"
                    else "sh -c cd ${file.parentFile.absolutePath} && java ${file.nameWithoutExtension}"
                )

                BufferedReader(process.inputReader()).lines().forEach(::println)

                BufferedReader(process.errorReader()).lines().forEach(::println)
            }
        }
    }
}