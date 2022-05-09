package org.casc.lang.compilation

import com.diogonunes.jcolor.AnsiFormat
import com.diogonunes.jcolor.Attribute
import org.casc.lang.ast.ClassInstance
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

class Compilation(private val preference: AbstractPreference) {
    companion object {
        val entryFormat = AnsiFormat(Attribute.CYAN_TEXT())
        const val outputFormat = "%-35s%s"
    }

    private var timings = mutableMapOf<String, Double>()

    fun compile() {
        var panic = false
        val sourceFile = preference.sourceFile!!.absoluteFile!!

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
                        val compilationUnit =
                            CompilationFileUnit(cascFile.name, source, relativeFilePath, cascFile.path)

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

                if (compilationUnits.anyError()) {
                    panic = true
                    return@measureTime
                }

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
                        parseResult?.let {
                            cascFile.file = it
                        }
                    }
                }

                compilationUnits.printReports()

                if (compilationUnits.anyError()) {
                    panic = true
                    return@measureTime
                }

                measureTime("Check (Prelude)") {
                    // Caches class for dummy type checking, used in declaration checking
                    Table.cachedClasses += compilationUnits.map { it.file.typeInstance.reference to it.file }

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
                        val (declarationCheckingReports, classScope) = checker.checkDeclaration(file)

                        compilationUnit.reports += declarationCheckingReports
                        compilationUnit.scope = classScope
                    }

                    compilationUnits.printReports()

                    if (compilationUnits.anyError()) {
                        panic = true
                        return@measureTime
                    }

                    Table.cachedClasses.clear()

                    // Define temporary class through bytecode for ASM library to process (See [getClassLoader][org.casc.lang.asm.CommonClassWriter])
                    val creationQueue = LinkedHashSet<String>()
                    val declarations = compilationUnits.map(CompilationFileUnit::file)

                    fun addToQueue(file: File) {
                        val typeInstance = file.typeInstance

                        if (typeInstance is ClassInstance && typeInstance.impl?.parentClassReference != null) {
                            val parentClazzFile =
                                declarations.find { it.typeInstance.reference.fullQualifiedPath == typeInstance.impl!!.parentClassReference!!.fullQualifiedPath }
                            if (parentClazzFile != null)
                                addToQueue(parentClazzFile)
                            creationQueue.add(file.typeInstance.reference.fullQualifiedPath)
                        } else {
                            creationQueue.add(file.typeInstance.reference.fullQualifiedPath)
                        }
                    }

                    declarations.forEach(::addToQueue)

                    for (name in creationQueue) {
                        val cachedFile =
                            declarations.find { it.typeInstance.reference.fullQualifiedPath == name }!!

                        val bytecode = Emitter(preference, true).emit(cachedFile)

                        preference.classLoader.defineClass(name, bytecode)
                    }

                    Table.cachedClasses += declarations.map { it.typeInstance.reference to it }
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

                if (compilationUnits.anyError()) {
                    panic = true
                    return@measureTime
                }

                measureTime("Emit") {
                    for (compilationUnit in compilationUnits) {
                        // Unit IV: Emitter
                        /**
                         * Emits AST into backend languages, like JVM bytecode.
                         * only JVM backend is available at this moment.
                         */
                        Emitter(preference, false).emit(compilationUnit.file)
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

                reports.forEach { it.printReport(preference, outputFileName, source) }

                if (reports.hasError()) {
                    panic = true
                    return@measureTime
                }

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

                reports.forEach { it.printReport(preference, outputFileName, source) }

                if (reports.hasError()) {
                    panic = true
                    return@measureTime
                }

                val checker = Checker(preference)
                var scope: Scope? = null

                measureTime("Check (Prelude)") {
                    // Unit III: Checker
                    /**
                     * Checks complex syntax validity and variables' type.
                     */
                    val (declarationReports, classScope) = checker.checkDeclaration(file!!)

                    reports = declarationReports
                    scope = classScope

                    Table.cachedClasses += file!!.typeInstance.reference to file
                }

                reports.forEach { it.printReport(preference, outputFileName, source) }

                if (reports.hasError()) {
                    panic = true
                    return@measureTime
                }

                measureTime("Check (Main)") {
                    val (bodyReports, finalFile) = checker.check(file!!, scope!!)

                    file = finalFile
                    reports = bodyReports
                }

                reports.forEach { it.printReport(preference, outputFileName, source) }

                if (reports.hasError()) {
                    panic = true
                    return@measureTime
                }

                reports.forEach { it.printReport(preference, outputFileName, source) }

                if (reports.hasError()) {
                    panic = true
                    return@measureTime
                }

                measureTime("Emit") {
                    // Unit IV: Emitter
                    /**
                     * Emits AST into backend languages, like JVM bytecode.
                     * only JVM backend is available at this moment.
                     */
                    Emitter(preference, false).emit(file!!)
                }
            }
        }

        if (preference.compileAndRun && !panic) {
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

    private fun List<CompilationFileUnit>.anyError(): Boolean = this.any(CompilationFileUnit::anyError)

    private fun List<Report>.hasError(): Boolean = this.filterIsInstance<Error>().isNotEmpty()

    private fun List<CompilationFileUnit>.printReports() = this.forEach {
        it.printReports(preference)
    }
}