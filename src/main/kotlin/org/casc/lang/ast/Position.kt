package org.casc.lang.ast

data class Position(val lineNumber: Int, var start: Int, var end: Int) {
    companion object {
        fun fromMultipleAndExtend(vararg positions: Position?): Position? {
            val firstNonNullIndex = positions.indexOfFirst { it != null }

            return if (firstNonNullIndex != -1) {
                val firstNonNull = positions[firstNonNullIndex]!!.copy()

                for (i in positions.indices) {
                    if (i == firstNonNullIndex) continue

                    firstNonNull.extendSelf(positions[i])
                }

                firstNonNull
            } else null
        }
    }

    constructor(lineNumber: Int, start: Int) : this(lineNumber, start, start + 1)

    fun extendSelf(start: Int = 0, end: Int = 1): Position {
        if (start == 0) {
            this.end += end
        } else {
            this.start -= start
            this.end += end
        }

        return this
    }

    fun extend(start: Int = 0, end: Int = 1): Position =
        copy().extendSelf(start, end)

    fun extendSelf(other: Position?): Position =
        if (other == null) this
        else if (lineNumber != other.lineNumber) this
        else {
            val clone = copy()
            if (start > other.start) clone.start = other.start
            if (end < other.end) clone.end = other.end

            clone
        }

    fun extend(other: Position?): Position =
        copy().extendSelf(other)

    override fun toString(): String =
        "$lineNumber:$start"
}
