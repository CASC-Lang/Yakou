package org.yakou.lang.compilation

import chaos.unity.nenggao.CharacterSet
import com.diogonunes.jcolor.Ansi
import com.diogonunes.jcolor.Attribute
import java.io.File
import java.time.Duration
import java.time.Instant
import org.yakou.lang.api.AbstractPreference
import org.yakou.lang.bind.Table
import org.yakou.lang.gen.jvm.JvmBytecodeGenerator
import org.yakou.lang.util.Constants

class CompilationSession(val preference: AbstractPreference) {
    val table: Table = Table()
    private val sourceFile: File? = preference.sourceFile
    private val unitProcessResult: MutableMap<String, Pair<UnitProcessResult, Long>> = mutableMapOf()

    fun compile() {
        if (sourceFile == null || !sourceFile.exists()) {
            return
        }

        if (sourceFile.isDirectory) {
            compileDirectory()
        } else {
            compileSingleSource()
        }

        if (preference.enableTiming) {
            val unitNamePadding = if (preference.enableColor) 30 else 20
            val statusPadding = if (preference.enableColor) 10 else 1

            for ((unitName, result) in unitProcessResult) {
                val status = result.first.stateLiteral

                println(
                    "%-${unitNamePadding}s %s status: %-${statusPadding}s | elapsed time: %d ms".format(
                        if (preference.enableColor) Ansi.colorize(unitName, Attribute.CYAN_TEXT()) else unitName,
                        if (preference.useAscii) CharacterSet.ASCII.rightArrow else CharacterSet.UNICODE.rightArrow,
                        if (preference.enableColor) {
                            Ansi.colorize(
                                status,
                                when (result.first) {
                                    UnitProcessResult.PASSED -> Attribute.GREEN_BACK()
                                    UnitProcessResult.FAILED -> Attribute.RED_BACK()
                                    UnitProcessResult.SKIPPED -> Attribute.BRIGHT_BLACK_BACK()
                                },
                                Attribute.BLACK_TEXT(),
                            )
                        } else {
                            status
                        },
                        result.second,
                    ),
                )
            }
        }
    }

    private fun compileDirectory() {
        val compilationUnits = sourceFile!!.walk()
            .filter { Constants.VALID_YAKOU_FILE_EXTENSIONS.contains(it.extension) }
            .map { CompilationUnit(it, this) }
            .toList()

        // PHASE I: LEXICAL ANALYSIS
        unitProcessResult["lexical analysis"] = measureTime {
            compilationUnits.processAll(CompilationUnit::lex)
        }

        if (!unitProcessResult["lexical analysis"]!!.first.ok()) {
            return
        }

        // PHASE II: SYNTACTIC ANALYSIS
        unitProcessResult["syntactic analysis"] = measureTime {
            compilationUnits.processAll(CompilationUnit::parse)
        }

        if (!unitProcessResult["syntactic analysis"]!!.first.ok()) {
            return
        }

        // PHASE III: DECLARATION BINDING
        unitProcessResult["declaration binding"] = measureTime {
            compilationUnits.processAll(CompilationUnit::bind)
        }

        if (!unitProcessResult["declaration binding"]!!.first.ok()) {
            return
        }

        // PHASE IV: TYPE BINDING
        unitProcessResult["type binding"] = measureTime {
            compilationUnits.processAll(CompilationUnit::postBind)
        }

        if (!unitProcessResult["type binding"]!!.first.ok()) {
            return
        }

        // PHASE V: SEMANTIC CHECKING
        unitProcessResult["semantic checking"] = measureTime {
            compilationUnits.processAll(CompilationUnit::check)
        }

        if (!unitProcessResult["semantic checking"]!!.first.ok()) {
            return
        }

        // PHASE VI: CODE OPTIMIZATION
        if (!preference.noOpt) {
            unitProcessResult["code optimization"] = measureTime {
                compilationUnits.processAll(CompilationUnit::optimize)
            }

            if (!unitProcessResult["code optimization"]!!.first.ok()) {
                return
            }
        } else {
            unitProcessResult["code optimization"] = UnitProcessResult.SKIPPED to 0
        }

        // PHASE VII: CODE GENERATION
        unitProcessResult["code generation"] = measureTime {
            val generator = JvmBytecodeGenerator(this)

            for (compilationUnit in compilationUnits)
                generator.gen(compilationUnit)

            generator.finalize()

            UnitProcessResult.PASSED
        }
    }

    private fun compileSingleSource() {
        val compilationUnit = CompilationUnit(sourceFile!!, this)

        // PHASE I: LEXICAL ANALYZE
        unitProcessResult["lexical analysis"] = measureTime(compilationUnit::lex)

        if (!unitProcessResult["lexical analysis"]!!.first.ok()) {
            return
        }

        // PHASE II: SYNTACTIC ANALYSIS
        unitProcessResult["syntactic analysis"] = measureTime(compilationUnit::parse)

        if (!unitProcessResult["syntactic analysis"]!!.first.ok()) {
            return
        }

        // PHASE III: DECLARATION BINDING
        unitProcessResult["declaration binding"] = measureTime(compilationUnit::bind)

        if (!unitProcessResult["declaration binding"]!!.first.ok()) {
            return
        }

        // PHASE IV: TYPE BINDING
        unitProcessResult["type binding"] = measureTime(compilationUnit::postBind)

        if (!unitProcessResult["type binding"]!!.first.ok()) {
            return
        }

        // PHASE V: SEMANTIC CHECKING
        unitProcessResult["semantic checking"] = measureTime(compilationUnit::check)

        if (!unitProcessResult["semantic checking"]!!.first.ok()) {
            return
        }

        // PHASE VI: CODE OPTIMIZATION
        if (!preference.noOpt) {
            unitProcessResult["code optimization"] = measureTime(compilationUnit::optimize)

            if (!unitProcessResult["code optimization"]!!.first.ok()) {
                return
            }
        } else {
            unitProcessResult["code optimization"] = UnitProcessResult.SKIPPED to 0
        }

        // PHASE VII: CODE GENERATION
        unitProcessResult["code generation"] = measureTime {
            val generator = JvmBytecodeGenerator(this)
            generator.gen(compilationUnit)
            generator.finalize()

            UnitProcessResult.PASSED
        }
    }

    private inline fun measureTime(crossinline process: () -> UnitProcessResult): Pair<UnitProcessResult, Long> {
        val before = Instant.now()
        val result = process()
        val after = Instant.now()
        return result to Duration.between(before, after).toMillis()
    }

    private inline fun List<CompilationUnit>.processAll(crossinline process: CompilationUnit.() -> UnitProcessResult): UnitProcessResult =
        if (map(process).any { it == UnitProcessResult.FAILED }) {
            UnitProcessResult.FAILED
        } else {
            UnitProcessResult.PASSED
        }
}
