package org.casclang.casc.compilation

import com.andreapivetta.kolor.red

data class TextSpan constructor(val filePath: String, val startLine: Int, val startPos: Int, val endPos: Int) {
    fun print(message: String): Unit =
        println(toString(message).red())

    override fun toString(): String =
        "$filePath: ($startLine, $startPos)"

    private fun toString(message: String): String =
        "${toString()}: $message"
}
