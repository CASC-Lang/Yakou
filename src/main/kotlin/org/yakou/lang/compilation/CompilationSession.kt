package org.yakou.lang.compilation

import chaos.unity.nenggao.CharacterSet
import com.diogonunes.jcolor.Ansi
import com.diogonunes.jcolor.Attribute
import org.yakou.lang.api.AbstractPreference
import org.yakou.lang.bind.Table
import org.yakou.lang.gen.jvm.JvmBytecodeGenerator
import org.yakou.lang.util.Constants
import java.io.File
import java.time.Duration
import java.time.Instant

class CompilationSession(val preference: AbstractPreference) {
    val table: Table = Table()
    private val sourceFile: File? = preference.sourceFile
    private val unitProcessResult: MutableMap<String, Pair<Boolean, Long>> = mutableMapOf()

    fun compile() {
        if (sourceFile == null || !sourceFile.exists())
            return

        if (sourceFile.isDirectory) {
            compileDirectory()
        } else {
            compileSingleSource()
        }

        if (preference.enableTiming) {
            val unitNamePadding = if (preference.enableColor) 30 else 20
            val statusPadding = if (preference.enableColor) 10 else 1

            for ((unitName, result) in unitProcessResult) {
                val status = when (result.first) {
                    true -> if (preference.useAscii) "V" else "✔"
                    false -> if (preference.useAscii) "X" else "✖"
                }

                println(
                    "%-${unitNamePadding}s %s status: %-${statusPadding}s | elapsed time: %d ms".format(
                        if (preference.enableColor) Ansi.colorize(unitName, Attribute.CYAN_TEXT()) else unitName,
                        if (preference.useAscii) CharacterSet.ASCII.rightArrow else CharacterSet.UNICODE.rightArrow,
                        if (preference.enableColor) Ansi.colorize(
                            status,
                            if (result.first) Attribute.GREEN_BACK() else Attribute.RED_BACK(),
                            Attribute.BLACK_TEXT()
                        ) else status,
                        result.second
                    )
                )
            }
        }
    }

    private fun compileDirectory() {
        val compilationUnits = sourceFile!!.walk()
            .filter { Constants.VALID_YAKOU_FILE_EXTENSIONS.contains(it.extension) }
            .map { CompilationUnit(it, this) }

        // PHASE I: LEXICAL ANALYSIS
        unitProcessResult["lexical analysis"] = measureTime {
            compilationUnits.all(CompilationUnit::lex)
        }

        if (!unitProcessResult["lexical analysis"]!!.first)
            return

        // PHASE II: SYNTACTIC ANALYSIS
        unitProcessResult["syntactic analysis"] = measureTime {
            compilationUnits.all(CompilationUnit::parse)
        }

        if (!unitProcessResult["syntactic analysis"]!!.first)
            return

        // PHASE III: TYPE BINDING
        unitProcessResult["type binding"] = measureTime {
            compilationUnits.all(CompilationUnit::bind)
        }

        if (!unitProcessResult["type binding"]!!.first)
            return

        // PHASE III: SEMANTIC CHECKING
        unitProcessResult["semantic checking"] = measureTime {
            compilationUnits.all(CompilationUnit::check)
        }

        if (!unitProcessResult["semantic checking"]!!.first)
            return

        // PHASE IV: CODE GENERATION
        unitProcessResult["semantic checking"] = measureTime {
            val generator = JvmBytecodeGenerator(this)

            for (compilationUnit in compilationUnits)
                generator.gen(compilationUnit)

            generator.finalize()

            true
        }
    }

    private fun compileSingleSource() {
        val compilationUnit = CompilationUnit(sourceFile!!, this)

        // PHASE I: LEXICAL ANALYZE
        unitProcessResult["lexical analysis"] = measureTime(compilationUnit::lex)

        if (!unitProcessResult["lexical analysis"]!!.first)
            return

        // PHASE II: SYNTACTIC ANALYSIS
        unitProcessResult["syntactic analysis"] = measureTime(compilationUnit::parse)

        if (!unitProcessResult["syntactic analysis"]!!.first)
            return

        // PHASE III: TYPE BINDING
        unitProcessResult["type binding"] = measureTime(compilationUnit::bind)

        if (!unitProcessResult["type binding"]!!.first)
            return

        // PHASE III: SEMANTIC CHECKING
        unitProcessResult["semantic checking"] = measureTime(compilationUnit::check)

        if (!unitProcessResult["semantic checking"]!!.first)
            return

        // PHASE IV: CODE GENERATION
        unitProcessResult["semantic checking"] = measureTime {
            val generator = JvmBytecodeGenerator(this)
            generator.gen(compilationUnit)
            generator.finalize()

            true
        }
    }

    private inline fun measureTime(crossinline process: () -> Boolean): Pair<Boolean, Long> {
        val before = Instant.now()
        val result = process()
        val after = Instant.now()
        return result to Duration.between(before, after).toMillis()
    }
}