package org.casc.lang.ast

data class Position(val lineNumber: Int, var start: Int, var end: Int) {
    constructor(lineNumber: Int, start: Int): this(lineNumber, start, start + 1)

    fun extend(start: Int = 0, end: Int = 1): Position {
        if (start == 0) {
            this.end += end
        } else {
            this.start -= start
            this.end += end
        }

        return this
    }

    // extend function only extends its start and end position but lineNumber
    fun extend(other: Position?): Position =
        if (other == null) this
        else if (lineNumber != other.lineNumber) this
        else {
            if (start > other.start) start = other.start
            if (end < other.end) end = other.end

            this
        }

    override fun toString(): String =
        "$lineNumber:$start"
}
