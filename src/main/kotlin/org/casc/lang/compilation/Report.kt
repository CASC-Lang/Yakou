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
        override val hint: String? = null
    ) : Report() {
        constructor(message: String) : this(null, message)
    }

    data class Error(
        override val position: Position?,
        override val message: String,
        override val hint: String? = null
    ) : Report() {
        constructor(message: String) : this(null, message)
    }

    fun printReport(filePath: String, source: List<String>) {
        if (position != null) {
            val (lineNumber, start, end) = position!!
            val preExtend =
                if (lineNumber > 1 && lineNumber.toString().length != (lineNumber - 1).toString().length) " "
                else ""
            val postExtend =
                if (lineNumber < source.lastIndex && lineNumber.toString().length != (lineNumber + 1).toString().length) " "
                else ""

            if (GlobalPreference.enableColor) {
                print(
                    when (this) {
                        is Warning -> Ansi.colorize("warning: ", reportAttribute[0])
                        is Error -> Ansi.colorize("error: ", reportAttribute[1])
                    }
                )
            } else {
                print(
                    when (this) {
                        is Warning -> "warning: "
                        is Error -> "error: "
                    }
                )
            }

            println(if (GlobalPreference.enableColor) Ansi.colorize(message, reportAttribute[2]) else message)
            println("--> $filePath:$position")

            if (lineNumber > 1) {
                println(
                    "${
                        if (GlobalPreference.enableColor) Ansi.colorize(
                            "${lineNumber - 1}$postExtend$preExtend | ",
                            reportAttribute[2]
                        ) else "${lineNumber - 1}$postExtend$preExtend | "
                    }${source[lineNumber - 2]}"
                )
            }

            println(
                "${
                    if (GlobalPreference.enableColor) Ansi.colorize(
                        "$lineNumber$postExtend | ",
                        reportAttribute[2]
                    ) else "$lineNumber$postExtend | "
                }${source[lineNumber - 1]}"
            )
            print(" ".repeat(start + 3 + lineNumber.toString().length))
            print(postExtend)
            print(
                if (GlobalPreference.enableColor) Ansi.colorize(
                    "^".repeat(end - start),
                    reportAttribute[1]
                ) else "^".repeat(end - start)
            )

            if (hint != null) {
                println(
                    if (GlobalPreference.enableColor) Ansi.colorize(
                        "= hint: $hint", reportAttribute[when (this) {
                            is Warning -> 0
                            is Error -> 1
                        }]
                    ) else "= hint: $hint"
                )
            } else {
                println()
            }

            if (lineNumber < source.lastIndex) {
                println(
                    "${
                        if (GlobalPreference.enableColor) Ansi.colorize(
                            "${lineNumber + 1} | ",
                            reportAttribute[2]
                        ) else "${lineNumber + 1} | "
                    }${source[lineNumber]}"
                )
            }
        } else {
            if (GlobalPreference.enableColor) {
                print(
                    when (this) {
                        is Warning -> Ansi.colorize("warning: ", reportAttribute[0])
                        is Error -> Ansi.colorize("error: ", reportAttribute[1])
                    }
                )
            } else {
                print(
                    when (this) {
                        is Warning -> "warning: "
                        is Error -> "error: "
                    }
                )
            }

            println(if (GlobalPreference.enableColor) Ansi.colorize(message, reportAttribute[2]) else message)
            println("--> $filePath")
        }
    }
}
