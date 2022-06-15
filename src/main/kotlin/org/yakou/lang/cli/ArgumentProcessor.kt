package org.yakou.lang.cli

import chaos.unity.nenggao.FileReportBuilder
import chaos.unity.nenggao.Span
import com.diogonunes.jcolor.Ansi
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

    private val command: String = System.getProperty("sun.java.command")
    private val commandReporter: FileReportBuilder = FileReportBuilder.source("$command\n").sourceName("Command line")
    private val markedFlags: MutableMap<String, Span> = mutableMapOf()
    private val preference: AbstractPreference = DefaultPreference()
    private var pos: Int = 0

    fun processPreference(): AbstractPreference {
        val options = Options()

        options.addOption(
            Option.builder("o")
                .longOpt("output")
                .hasArg()
                .numberOfArgs(1)
                .desc("output folder for compiled classes")
                .build()
        )

        options.addOption(
            Option.builder("a")
                .longOpt("ascii")
                .desc("shows warning and error messages in ascii form")
                .build()
        )

        // Discard first argument, it's program's entry point
        nextArg()

        while (pos < command.length) {
            skipSpaces()
            val (flag, flagSpan) = nextArg()

            if (flag == "-o" || flag == "-output") {
                processOutputFile(flag, flagSpan)
            } else if (flag == "-t" || flag == "-timing") {
                if (checkReassignment("timing", flag, flagSpan))
                    continue

                preference.enableTiming = true
                markedFlags["timing"] = flagSpan
            } else if (flag == "-a" || flag == "-ascii") {
                if (checkReassignment("ascii", flag, flagSpan))
                    continue

                preference.useAscii = true
                markedFlags["ascii"] = flagSpan
            } else if (flag == "-c" || flag == "-color") {
                if (checkReassignment("color", flag, flagSpan))
                    continue

                preference.enableColor = true
                markedFlags["color"] = flagSpan
            } else if (preference.sourceFile == null) {
                val sourceFile = File(flag)

                if (!sourceFile.exists()) {
                    commandReporter.error(COMMON_SPAN, "Invalid source file, file does not exist")
                        .label(flagSpan, "Source file must be an existed file or folder")
                        .color(Attribute.RED_TEXT())
                        .build().build()
                    continue
                }

                preference.sourceFile = sourceFile
            } else {
                commandReporter.warning(COMMON_SPAN, "Redundant argument")
                    .label(flagSpan, "Unused argument here")
                    .color(Attribute.YELLOW_TEXT())
                    .build().build()
            }
        }

        if (preference.sourceFile == null) {
            commandReporter.error(COMMON_SPAN, "Invalid compilation session: source file is not provided")
                .build()
        }

        commandReporter.print(System.out)

        return preference
    }

    private fun processOutputFile(flag: String, flagSpan: Span) {
        skipSpaces()

        val (folderPath, folderSpan) = nextArg()

        if (folderPath.isBlank()) {
            val colorizedFlag = Ansi.colorize(flag, Attribute.BRIGHT_CYAN_TEXT())

            commandReporter.error(COMMON_SPAN, "Not enough value for flag `$colorizedFlag`")
                .label(flagSpan, "Flag `$colorizedFlag` requires 1 value")
                .color(Attribute.RED_TEXT())
                .build().build()
            return
        }

        if (checkReassignment("output", flag, flagSpan))
            return

        val folder = File(folderPath)

        if (!folder.exists()) {
            preference.outputFolder = folder
            markedFlags["output"] = flagSpan
            return
        }

        if (!folder.isDirectory) {
            commandReporter.error(COMMON_SPAN, "Invalid output directory")
                .label(folderSpan, "Not a folder")
                .color(Attribute.RED_TEXT())
                .build()
                .label(flagSpan, "Required by this flag")
                .color(Attribute.CYAN_TEXT())
                .build().build()
            return
        }

        preference.outputFolder = folder
        markedFlags["output"] = flagSpan
    }

    /**
     * Returns false when flag haven't been assigned
     */
    private fun checkReassignment(flag: String, flagName: String, reassignmentFlagSpan: Span): Boolean =
        if (markedFlags.containsKey(flag)) {
            val colorizedFlag = Ansi.colorize(flagName, Attribute.BRIGHT_CYAN_TEXT())

            commandReporter.warning(COMMON_SPAN, "Reassignment of flag `%s`", colorizedFlag)
                .label(markedFlags[flag]!!, "First assignment of flag `%s`", colorizedFlag)
                .color(Attribute.CYAN_TEXT())
                .build()
                .label(reassignmentFlagSpan, "Reassignment of flag `%s`", colorizedFlag)
                .color(Attribute.YELLOW_TEXT())
                .build().build()
            true
        } else false

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