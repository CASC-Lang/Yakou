package org.casc.lang.compilation

import com.diogonunes.jcolor.Ansi
import com.diogonunes.jcolor.Attribute.*
import org.casc.lang.ast.Position

sealed class Report {
    companion object {
        private val reportAttribute = arrayOf(YELLOW_TEXT(), RED_TEXT(), BLUE_TEXT())
    }

    abstract val position: Position?
    abstract val message: String
    abstract val hint: String?

    data class Warning(
        override val position: Position?,
        override val message: String,
        override val hint: String? = null,
    ) : Report() {
        constructor(message: String) : this(null, message)
    }

    data class Error(
        override val position: Position?,
        override val message: String,
        override val hint: String? = null,
    ) : Report() {
        constructor(message: String) : this(null, message)
    }

    fun printReport(preference: AbstractPreference) {
        var finalMessage = StringBuilder(
            when {
                preference.enableColor -> {
                    when (this) {
                        is Warning -> Ansi.colorize("warning: ", reportAttribute[0])
                        is Error -> Ansi.colorize("error: ", reportAttribute[1])
                    }
                }
                else -> {
                    when (this) {
                        is Warning -> "warning: "
                        is Error -> "error: "
                    }
                }
            }
        )


        finalMessage += if (preference.enableColor) Ansi.colorize(message, reportAttribute[2]) else message
        println(finalMessage.toString())
    }

    fun printReport(preference: AbstractPreference, filePath: String, source: List<String>) {
        var finalMessage = StringBuilder(
            when {
                preference.enableColor -> {
                    when (this) {
                        is Warning -> Ansi.colorize("warning: ", reportAttribute[0])
                        is Error -> Ansi.colorize("error: ", reportAttribute[1])
                    }
                }
                else -> {
                    when (this) {
                        is Warning -> "warning: "
                        is Error -> "error: "
                    }
                }
            }
        )

        if (position != null) {
            val (lineNumber, start, end) = position!!
            val preExtend =
                if (lineNumber > 1 && lineNumber.toString().length != (lineNumber - 1).toString().length) " "
                else ""
            val postExtend =
                if (lineNumber < source.lastIndex && lineNumber.toString().length != (lineNumber + 1).toString().length) " "
                else ""

            finalMessage += if (preference.enableColor) Ansi.colorize(message, reportAttribute[2]) else message
            finalMessage += "\n--> $filePath:$position\n"

            if (lineNumber > 1) {
                finalMessage +=
                    "${
                        if (preference.enableColor) Ansi.colorize(
                            "${lineNumber - 1}$postExtend$preExtend |",
                            reportAttribute[2]
                        ) else "${lineNumber - 1}$postExtend$preExtend |"
                    }${if (source[lineNumber - 2].isBlank()) "" else " ${source[lineNumber - 2]}"}\n"
            }

            finalMessage +=
                "${
                    if (preference.enableColor) Ansi.colorize(
                        "$lineNumber$postExtend |",
                        reportAttribute[2]
                    ) else "$lineNumber$postExtend |"
                }${if (source[lineNumber - 1].isBlank()) "" else " ${source[lineNumber - 1]}"}\n"

            finalMessage += " ".repeat(start + 3 + lineNumber.toString().length)
            finalMessage += postExtend
            finalMessage += if (preference.enableColor) Ansi.colorize(
                "^".repeat(end - start),
                when (this) {
                    is Warning -> reportAttribute[0]
                    is Error -> reportAttribute[1]
                }
            ) else "^".repeat(end - start)

            finalMessage += if (hint != null) {
                if (preference.enableColor) Ansi.colorize(
                    "= hint: $hint\n", reportAttribute[when (this) {
                        is Warning -> 0
                        is Error -> 1
                    }]
                ) else "= hint: $hint\n"
            } else {
                "\n"
            }

            if (lineNumber <= source.lastIndex) {
                finalMessage += "${
                    if (preference.enableColor) Ansi.colorize(
                        "${lineNumber + 1} |",
                        reportAttribute[2]
                    ) else "${lineNumber + 1} |"
                }${if (source[lineNumber].isBlank()) "" else " ${source[lineNumber]}"}\n"
            }
        } else {
            finalMessage += if (preference.enableColor) Ansi.colorize(message, reportAttribute[2]) else message
            finalMessage += "\n--> $filePath\n"
        }

        print(finalMessage)
    }

    operator fun StringBuilder.plusAssign(string: String) {
        this.append(string)
    }
}
