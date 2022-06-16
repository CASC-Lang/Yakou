package org.yakou.lang.compilation

import chaos.unity.nenggao.CharacterSet
import com.diogonunes.jcolor.Ansi
import com.diogonunes.jcolor.Attribute
import org.yakou.lang.api.AbstractPreference
import org.yakou.lang.util.Constants
import java.io.File
import java.time.Duration
import java.time.Instant

class CompilationSession(private val preference: AbstractPreference) {
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
            for ((unitName, result) in unitProcessResult) {
                println(
                    "%-30s %s status: %-17s | elapsed time: %d ms".format(
                        if (preference.enableColor) Ansi.colorize(unitName, Attribute.CYAN_TEXT()) else unitName,
                        if (preference.useAscii) CharacterSet.ASCII.rightArrow else CharacterSet.UNICODE.rightArrow,
                        if (preference.enableColor) Ansi.colorize(
                            result.first.toString(),
                            if (result.first) Attribute.GREEN_BACK() else Attribute.RED_BACK(),
                            Attribute.BLACK_TEXT()
                        ) else result.first.toString(),
                        result.second
                    )
                )
            }
        }
    }

    private fun compileDirectory() {
        val compilationUnits = sourceFile!!.walk()
            .filter { Constants.VALID_YAKOU_FILE_EXTENSIONS.contains(it.extension) }
            .map { CompilationUnit(it, preference) }

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
    }

    private fun compileSingleSource() {
        val compilationUnit = CompilationUnit(sourceFile!!, preference)

        // PHASE I: LEXICAL ANALYZE
        unitProcessResult["lexical analysis"] = measureTime(compilationUnit::lex)

        if (!unitProcessResult["lexical analysis"]!!.first)
            return

        // PHASE II: SYNTACTIC ANALYSIS
        unitProcessResult["syntactic analysis"] = measureTime(compilationUnit::parse)

        if (!unitProcessResult["syntactic analysis"]!!.first)
            return
    }

    private inline fun measureTime(crossinline process: () -> Boolean): Pair<Boolean, Long> {
        val before = Instant.now()
        val result = process()
        val after = Instant.now()
        return result to Duration.between(before, after).toMillis()
    }
}