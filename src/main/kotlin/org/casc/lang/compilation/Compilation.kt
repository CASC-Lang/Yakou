package org.casc.lang.compilation

import com.diogonunes.jcolor.AnsiFormat
import com.diogonunes.jcolor.Attribute
import org.casc.lang.ast.File
import org.casc.lang.ast.Token
import org.casc.lang.checker.Checker
import org.casc.lang.emitter.Emitter
import org.casc.lang.lexer.Lexer
import org.casc.lang.parser.Parser
import org.casc.lang.table.Scope
import org.casc.lang.table.Table
import java.io.BufferedReader
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import java.io.File as JFile

class Compilation(private val preference: AbstractPreference) {
    companion object {
        private fun List<CompilationFileUnit>.anyError(): Boolean = this.any(CompilationFileUnit::anyError)

        private fun List<Report>.hasError(): Boolean = this.filterIsInstance<Error>().isNotEmpty()

        private fun List<CompilationFileUnit>.printReports() = this.forEach(CompilationFileUnit::printReports)

        val entryFormat = AnsiFormat(Attribute.CYAN_TEXT())
        const val outputFormat = "%-35s%s"
    }

    private var timings = mutableMapOf<String, Double>()

    fun compile() {
        val sourceFile = preference.sourceFile!!

        measureTime("Compilation") {
            if (sourceFile.isDirectory) {
                // Init setup
                preference.outputDir.mkdir()

                // Compilations
                val cascFiles = sourceFile.walk().filter { it.isFile && it.extension == "casc" }.toList()
                val compilationUnits = mutableListOf<CompilationFileUnit>()

                measureTime("Lex") {
                    for (cascFile in cascFiles) {
                        val source = cascFile.readLines()
                        val relativeFilePath = cascFile.toRelativeString(sourceFile)
                        val compilationUnit = CompilationFileUnit(cascFile.name, source, relativeFilePath, cascFile.path)

                        // Unit I: Lexer
                        /**
                         * Known as lexical analyzer, handles source text parsing into token form.
                         */
                        val (lexReports, lexResult) = Lexer(preference).lex(source)

                        compilationUnit.reports += lexReports
                        compilationUnit.tokens = lexResult

                        compilationUnits += compilationUnit
                    }
                }

                compilationUnits.printReports()

                if (compilationUnits.anyError()) return@measureTime

                measureTime("Parse") {
                    for (cascFile in compilationUnits) {
                        // Unit II: Parser
                        /**
                         * Parse tokens into an Abstract Syntax Tree (aka AST) for further unit to use with,
                         * parser also asserts that the certainty of source code validity.
                         */
                        val (parseReports, parseResult) =
                            Parser(preference).parse(cascFile.filePath, cascFile.relativePath, cascFile.tokens)

                        cascFile.reports += parseReports
                        cascFile.file = parseResult
                    }
                }

                compilationUnits.printReports()

                if (compilationUnits.anyError()) return@measureTime

                measureTime("Check (Prelude)") {
                    // Caches class for dummy type checking, used in declaration checking
                    Table.cachedClasses += compilationUnits.map { it.file.clazz.getReference() to it.file }

                    for (compilationUnit in compilationUnits) {
                        val file = compilationUnit.file

                        // Unit III: Checker
                        /**
                         * Checks complex syntax validity and variables' type.
                         *
                         * Phase I:
                         * Check all AST declaration's validity
                         */
                        val checker = Checker(preference)
                        val (declarationCheckingReports, checkedFile, classScope) = checker.checkDeclaration(file)

                        compilationUnit.file = checkedFile
                        compilationUnit.reports += declarationCheckingReports
                        compilationUnit.scope = classScope
                    }

                    compilationUnits.printReports()

                    if (compilationUnits.anyError()) return@measureTime

                    Table.cachedClasses.clear()

                    // Define temporary class through bytecode for ASM library to process (See [getClassLoader][org.casc.lang.asm.CommonClassWriter])
                    val creationQueue = LinkedHashSet<String>()
                    val declarations = compilationUnits.map(CompilationFileUnit::file)

                    fun addToQueue(file: File) {
                        if (file.clazz.parentClassReference == null) {
                            creationQueue.add(file.clazz.getReference().fullQualifiedPath)
                        } else {
                            val parentClazzFile =
                                declarations.find { it.clazz.getReference().fullQualifiedPath == file.clazz.parentClassReference!!.fullQualifiedPath }
                            if (parentClazzFile != null)
                                addToQueue(parentClazzFile)
                            creationQueue.add(file.clazz.getReference().fullQualifiedPath)
                        }
                    }

                    declarations.forEach(::addToQueue)

                    for (name in creationQueue) {
                        val cachedFile =
                            declarations.find { it.clazz.getReference().fullQualifiedPath == name }!!

                        val bytecode = Emitter(preference, true).emit(
                            JFile(preference.outputDir, JFile(cachedFile.relativeFilePath).parentFile?.path ?: ""),
                            cachedFile
                        )

                        preference.classLoader.defineClass(name, bytecode)
                    }

                    Table.cachedClasses += declarations.map { it.clazz.getReference() to it }
                }

                measureTime("Check (Main)") {
                    for (compilationUnit in compilationUnits) {
                        /**
                         * Phase III:
                         * Check all AST declaration's body's validity
                         */
                        val checker = Checker(preference)
                        val (checkReports, checkResult) = checker.check(compilationUnit.file, compilationUnit.scope)

                        compilationUnit.file = checkResult
                        compilationUnit.reports = checkReports
                    }
                }

                compilationUnits.printReports()

                if (compilationUnits.anyError()) return@measureTime

                measureTime("Emit") {
                    for (compilationUnit in compilationUnits) {
                        // Unit IV: Emitter
                        /**
                         * Emits AST into backend languages, like JVM bytecode.
                         * only JVM backend is available at this moment.
                         */
                        Emitter(preference, false).emit(
                            JFile(
                                preference.outputDir,
                                JFile(compilationUnit.file.relativeFilePath).parentFile?.path ?: ""
                            ),
                            compilationUnit.file
                        )
                    }
                }
            } else if (sourceFile.isFile) {
                // Init preference
                preference.outputDir = sourceFile.parentFile

                val outputFileName = "./${sourceFile.name}"
                // Compilation
                val source = sourceFile.readLines()

                var reports: List<Report> = listOf()
                var tokens: List<Token> = listOf()

                measureTime("Lex") {
                    // Unit I: Lexer
                    /**
                     * Known as lexical analyzer, handles source text parsing into token form.
                     */
                    val (lexReports, lexResult) = Lexer(preference).lex(source)

                    reports = lexReports
                    tokens = lexResult
                }

                reports.forEach { it.printReport(outputFileName, source) }

                if (reports.hasError()) return@measureTime

                var file: File? = null

                measureTime("Parse") {
                    // Unit II: Parser
                    /**
                     * Parse tokens into an Abstract Syntax Tree (aka AST) for further unit to use with,
                     * parser also asserts that the certainty of source code validity.
                     */
                    val (parseReports, parseResult) = Parser(preference).parse(
                        sourceFile.absolutePath, sourceFile.toRelativeString(preference.outputDir), tokens
                    )

                    reports = parseReports
                    file = parseResult
                }

                reports.forEach { it.printReport(outputFileName, source) }

                if (reports.hasError()) return@measureTime

                val checker = Checker(preference)
                var scope: Scope? = null

                measureTime("Check (Prelude)") {
                    // Unit III: Checker
                    /**
                     * Checks complex syntax validity and variables' type.
                     */
                    val (declarationReports, declarationCheckedFile, classScope) = checker.checkDeclaration(file!!)

                    file = declarationCheckedFile
                    reports = declarationReports
                    scope = classScope
                }

                reports.forEach { it.printReport(outputFileName, source) }

                if (reports.hasError()) return@measureTime

                measureTime("Check (Main)") {
                    val (bodyReports, finalFile) = checker.check(file!!, scope!!)

                    file = finalFile
                    reports = bodyReports
                }

                reports.forEach { it.printReport(outputFileName, source) }

                if (reports.hasError()) return@measureTime

                reports.forEach { it.printReport(outputFileName, source) }

                if (reports.hasError()) return@measureTime

                measureTime("Emit") {
                    // Unit IV: Emitter
                    /**
                     * Emits AST into backend languages, like JVM bytecode.
                     * only JVM backend is available at this moment.
                     */
                    Emitter(preference, false).emit(sourceFile.parentFile, file!!)
                }
            }
        }

        if (preference.compileAndRun) {
            measureTime("Run") {
                val isWindows = System.getProperty("os.name").lowercase().startsWith("windows")
                val process = Runtime.getRuntime().exec(
                    if (isWindows) "cmd.exe /c cd ${sourceFile.parentFile.absolutePath} && java ${sourceFile.nameWithoutExtension}"
                    else "sh -c cd ${sourceFile.parentFile.absolutePath} && java ${sourceFile.nameWithoutExtension}"
                )

                BufferedReader(process.inputReader()).lines().forEach(::println)

                BufferedReader(process.errorReader()).lines().forEach(::println)
            }
        }

        if (preference.enableTiming) {
            for ((key, value) in timings) {
                println(outputFormat.format("${entryFormat.format(key)}:", "$value sec"))
            }
        }
    }

    @OptIn(ExperimentalTime::class)
    fun measureTime(programName: String, block: () -> Unit) {
        timings += programName to kotlin.time.measureTime(block).toDouble(DurationUnit.SECONDS)
    }
}