package org.yakou.lang.cli

import chaos.unity.nenggao.FileReportBuilder
import chaos.unity.nenggao.Span
import com.diogonunes.jcolor.Attribute
import org.apache.commons.cli.Option
import org.apache.commons.cli.Options
import org.yakou.lang.api.AbstractPreference
import org.yakou.lang.api.DefaultPreference
import java.io.File

class ArgumentProcessor {
    companion object {
        private val COMMON_SPAN = Span.range(1, 1)
    }

    private val command = System.getProperty("sun.java.command")
    private val commandReporter = FileReportBuilder.source("$command\n")
    private val preference = DefaultPreference()
    private var pos = 0

    fun processPreference(): AbstractPreference {
        val options = Options()

        options.addOption(
            Option.builder("o")
            .longOpt("output")
            .hasArg()
            .numberOfArgs(1)
            .desc("output folder for compiled classes")
            .build())

        options.addOption(
            Option.builder("a")
            .longOpt("ascii")
            .desc("shows warning and error messages in ascii form")
            .build())

        // Discard first argument, it's program's entry point
        nextArg()

        while (pos < command.length) {
            skipSpaces()
            val (flag, flagSpan) = nextArg()

            if (flag == "-o" || flag == "-output") {
                processOutputFile(flag, flagSpan)
            }
        }

        commandReporter.print(System.out)

        return preference
    }

    private fun processOutputFile(flag: String, flagSpan: Span) {
        skipSpaces()

        val (folderPath, folderSpan) = nextArg()

        if (folderPath.isBlank()) {
            commandReporter.error(COMMON_SPAN, "Not enough value for flag `$flag`")
                .label(flagSpan, "Flag `$flag` requires 1 value")
                .color(Attribute.RED_TEXT())
                .build()
                .build()
            return
        }

        val folder = File(folderPath)

        if (!folder.exists()) {
            folder.mkdirs()
            preference.outputFolder = folder
            return
        }

        if (!folder.isDirectory) {
            commandReporter.error(COMMON_SPAN, "Invalid output directory")
                .label(folderSpan, "Not a folder")
                .color(Attribute.RED_TEXT())
                .build()
                .label(flagSpan, "Required by this flag")
                .color(Attribute.CYAN_TEXT())
                .build()
                .build()
            return
        }
    }

    private fun skipSpaces() {
        while (pos < command.length && command[pos].isWhitespace())
            pos++
    }

    private fun nextArg(): Pair<String, Span> {
        val startPos = pos

        while (pos < command.length && !command[pos].isWhitespace())
            pos++

        val lastPos = pos
        return command.substring(startPos until lastPos) to Span.singleLine(1, startPos, lastPos)
    }
}