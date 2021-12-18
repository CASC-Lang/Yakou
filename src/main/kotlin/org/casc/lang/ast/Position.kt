package org.casc.lang.ast

data class Position(val lineNumber: Int, val start: Int, val end: Int) {
    constructor(lineNumber: Int, start: Int): this(lineNumber, start, start + 1)

    override fun toString(): String =
        "$lineNumber:$start"
}
