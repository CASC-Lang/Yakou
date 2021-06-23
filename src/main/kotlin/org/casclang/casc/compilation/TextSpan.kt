package org.casclang.casc.compilation

import com.andreapivetta.kolor.red

data class TextSpan constructor(val filePath: String, val startLine: Int, val startPos: Int, val length: Int) {
    val endPos = startPos + length

    fun println(message: String): Unit =
        println(toString(message).red())

    override fun toString(): String =
        "($startLine, $startPos, $endPos)"

    private fun toString(message: String): String =
        "${toString()}: $message"
}
