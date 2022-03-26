package org.casc.lang.compilation

import org.casc.lang.ast.File
import org.casc.lang.checker.Checker
import org.casc.lang.emitter.Emitter
import org.casc.lang.lexer.Lexer
import org.casc.lang.parser.Parser
import org.casc.lang.table.Table
import org.casc.lang.utils.*
import java.io.BufferedReader
import java.io.File as JFile

class Compilation(private val preference: AbstractPreference) {
    companion object {
        private fun List<CompilationFileUnit>.anyError(): Boolean = this.any(CompilationFileUnit::anyError)

        private fun List<Report>.hasError(): Boolean = this.filterIsInstance<Error>().isNotEmpty()

        private fun List<CompilationFileUnit>.printReports() = this.forEach(CompilationFileUnit::printReports)
    }

    fun compile() {
        val jfile = preference.sourceFile!!

        if (jfile.isDirectory) {
            // Init setup
            preference.outputDir.mkdir()

            // Compilations
            val cascFiles = jfile.walk().filter { it.isFile && it.extension == "casc" }.toList()
            val compilationUnits = mutableListOf<CompilationFileUnit>()

            for (cascFile in cascFiles) {
                val source = cascFile.readLines()
                val relativeFilePath = cascFile.toRelativeString(jfile)

                // Unit I: Lexer
                /**
                 * Known as lexical analyzer, handles source text parsing into token form.
                 */
                val (lexReports, lexResult) = Lexer(preference).lex(source)

                // Unit II: Parser
                /**
                 * Parse tokens into an Abstract Syntax Tree (aka AST) for further unit to use with,
                 * parser also asserts that the certainty of source code validity.
                 */
                val (parseReports, parseResult) =
                    Parser(preference).parse(cascFile.path, relativeFilePath, lexResult)

                val compilationUnit = CompilationFileUnit(cascFile.name, source, relativeFilePath)

                compilationUnit.reports += lexReports
                compilationUnit.reports += parseReports
                compilationUnit.file = parseResult

                compilationUnits += compilationUnit
            }

            compilationUnits.printReports()

            if (compilationUnits.anyError()) return

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

            if (compilationUnits.anyError()) return

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

            for (compilationUnit in compilationUnits) {
                /**
                 * Phase III:
                 * Check all AST declaration's body's validity
                 */
                val checker = Checker(preference)
                val (checkReports, checkResult) = checker.check(compilationUnit.file, compilationUnit.scope)

                checkReports.forEach { it.printReport(compilationUnit.relativePath, compilationUnit.source) }

                if (checkReports.filterIsInstance<Error>().isNotEmpty()) return

                // Unit IV: Emitter
                /**
                 * Emits AST into backend languages, like JVM bytecode.
                 * only JVM backend is available at this moment.
                 */
                Emitter(preference, false).emit(
                    JFile(preference.outputDir, JFile(compilationUnit.file.relativeFilePath).parentFile?.path ?: ""),
                    checkResult
                )
            }
        } else if (jfile.isFile) {
            // Init preference
            preference.outputDir = jfile.parentFile

            val outputFileName = "./${jfile.name}"
            // Compilation
            val source = jfile.readLines()

            // Unit I: Lexer
            /**
             * Known as lexical analyzer, handles source text parsing into token form.
             */
            val (lexReports, lexResult) = Lexer(preference).lex(source)

            lexReports.forEach { it.printReport(outputFileName, source) }

            if (lexReports.hasError()) return

            // Unit II: Parser
            /**
             * Parse tokens into an Abstract Syntax Tree (aka AST) for further unit to use with,
             * parser also asserts that the certainty of source code validity.
             */
            val (parseReports, parseResult) = Parser(preference).parse(
                jfile.absolutePath, jfile.toRelativeString(preference.outputDir), lexResult
            )

            parseReports.forEach { it.printReport(outputFileName, source) }

            if (parseReports.hasError()) return

            // Unit III: Checker
            /**
             * Checks complex syntax validity and variables' type.
             */
            val checker = Checker(preference)
            val (declarationReports, declarationCheckedFile, scope) = checker.checkDeclaration(parseResult)

            declarationReports.forEach { it.printReport(outputFileName, source) }

            if (declarationReports.hasError()) return

            val (checkReports, checkResult) = checker.check(declarationCheckedFile, scope)

            checkReports.forEach { it.printReport(outputFileName, source) }

            if (checkReports.hasError()) return

            // Unit IV: Emitter
            /**
             * Emits AST into backend languages, like JVM bytecode.
             * only JVM backend is available at this moment.
             */
            Emitter(preference, false).emit(jfile.parentFile, checkResult)

            if (preference.compileAndRun) {
                val isWindows = System.getProperty("os.name").lowercase().startsWith("windows")
                val process = Runtime.getRuntime().exec(
                    if (isWindows) "cmd.exe /c cd ${jfile.parentFile.absolutePath} && java ${jfile.nameWithoutExtension}"
                    else "sh -c cd ${jfile.parentFile.absolutePath} && java ${jfile.nameWithoutExtension}"
                )

                BufferedReader(process.inputReader()).lines().forEach(::println)

                BufferedReader(process.errorReader()).lines().forEach(::println)
            }
        }
    }
}