package org.casc.lang.ast

data class Position(val lineNumber: Int, var start: Int, var end: Int) {
    constructor(lineNumber: Int, start: Int) : this(lineNumber, start, start + 1)

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
            val clone = copy()
            if (start > other.start) clone.start = other.start
            if (end < other.end) clone.end = other.end

            clone
        }

    override fun toString(): String =
        "$lineNumber:$start"

    @JvmInline
    value class MutablePosition private constructor(val position: Position?) {
        companion object {
            fun fromMultipleAndExtend(vararg positions: Position?): MutablePosition {
                val firstNonNullIndex = positions.indexOfFirst { it != null }

                return if (firstNonNullIndex != -1) {
                    val firstNonNull = positions[firstNonNullIndex]!!

                    for (i in 0..positions.size) {
                        if (i == firstNonNullIndex) continue

                        firstNonNull.extend(positions[i])
                    }

                    MutablePosition(firstNonNull)
                } else MutablePosition(null)
            }
        }
    }
}
