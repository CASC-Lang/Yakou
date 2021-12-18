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

            print(
                when (this) {
                    is Warning -> Ansi.colorize("warning: ", reportAttribute[0])
                    is Error -> Ansi.colorize("error: ", reportAttribute[1])
                }
            )

            println(Ansi.colorize(message, reportAttribute[2]))
            println("--> $filePath:$position")

            if (lineNumber > 1) {
                println(
                    "${
                        Ansi.colorize(
                            "${lineNumber - 1}$postExtend$preExtend | ",
                            reportAttribute[2]
                        )
                    }${source[lineNumber - 2]}"
                )
            }

            println("${Ansi.colorize("$lineNumber$postExtend | ", reportAttribute[2])}${source[lineNumber - 1]}")
            print(" ".repeat(start + 3 + lineNumber.toString().length))
            print(postExtend)
            print(Ansi.colorize("^".repeat(end - start), reportAttribute[1]))

            if (hint != null) {
                println(Ansi.colorize("= hint: $hint", reportAttribute[when (this) {
                    is Warning -> 0
                    is Error -> 1
                }]))
            } else {
                println()
            }

            if (lineNumber < source.lastIndex) {
                println("${Ansi.colorize("${lineNumber + 1} | ", reportAttribute[2])}${source[lineNumber]}")
            }
        } else {
            when (this) {
                is Warning -> print("warning: ")
                is Error -> print("error: ")
            }

            println(message)
            println("--> <$filePath>(EMPTY SOURCE)")
        }
    }
}
