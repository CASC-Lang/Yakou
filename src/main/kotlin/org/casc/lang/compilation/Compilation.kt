package org.casc.lang.compilation

import com.diogonunes.jcolor.AnsiFormat
import com.diogonunes.jcolor.Attribute
import org.casc.lang.ast.ClassInstance
import org.casc.lang.ast.File
import org.casc.lang.ast.Token
import org.casc.lang.ast.TraitInstance
import org.casc.lang.checker.Checker
import org.casc.lang.emitter.Emitter
import org.casc.lang.lexer.Lexer
import org.casc.lang.parser.Parser
import org.casc.lang.table.Reference
import org.casc.lang.table.Scope
import org.casc.lang.table.Table
import java.io.BufferedReader
import java.util.*
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

                measureTime("Check (Prelude)") checkPrelude@{
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

                    if (compilationUnits.anyError()) {
                        panic = true
                        return@checkPrelude
                    }

                    val creationQueue = mutableListOf<Reference>()
                    val declarations = compilationUnits.map(CompilationFileUnit::file)

                    for ((file, compilationUnit) in declarations.zip(compilationUnits)) {
                        // Check parent class has no cyclic inheritance
                        val (_, _, _, typeInstance) = file

                        creationQueue += typeInstance.reference

                        when (typeInstance) {
                            is ClassInstance -> {
                                val parentClassReferenceSet = hashSetOf<Reference>()
                                var currentTypeInstance = typeInstance

                                while (currentTypeInstance.impl != null && currentTypeInstance.impl!!.parentClassReference != null) {
                                    currentTypeInstance =
                                        Table.findTypeInstance(currentTypeInstance.impl!!.parentClassReference!!)
                                            ?: break

                                    if (!parentClassReferenceSet.add(currentTypeInstance.typeReference)) {
                                        // Cyclic inheritance
                                        compilationUnit.reports += Error(
                                            typeInstance.impl!!.parentClassReference!!.pos,
                                            "Circular inheritance is forbidden",
                                            "Class ${typeInstance.reference.asCASCStyle()} inherits class ${currentTypeInstance.reference.asCASCStyle()} but class ${currentTypeInstance.reference.asCASCStyle()} also inherits class ${typeInstance.reference.asCASCStyle()}"
                                        )
                                        break
                                    } else creationQueue.add(0, currentTypeInstance.reference)
                                }
                            }
                            is TraitInstance -> {}
                        }
                    }

                    if (compilationUnits.anyError()) {
                        panic = true
                        return@checkPrelude
                    }

                    // Define temporary class through bytecode for ASM library to process (See [getClassLoader][org.casc.lang.asm.CommonClassWriter])
                    for (reference in creationQueue.distinct()) {
                        val cachedFile = Table.findFile(reference)!!
                        val bytecode = Emitter(preference, true).emit(cachedFile)

                        preference.classLoader.defineClass(reference.fullQualifiedPath, bytecode)
                    }
                }

                compilationUnits.printReports()

                if (compilationUnits.anyError()) {
                    panic = true
                    return@measureTime
                }

                measureTime("Check (Main)") {
                    for (compilationUnit in compilationUnits) {
                        /**
                         * Phase III:
                         * Check all AST declaration's body's validity
                         */
                        val checker = Checker(preference)
                        val checkReports = checker.check(compilationUnit.file, compilationUnit.scope)

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
                    val bodyReports = checker.check(file!!, scope!!)

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