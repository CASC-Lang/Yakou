package org.casc.lang.utils

/**
 * MutableObjectSet is mostly same as HashSet, but it requires users to implement a stricter duplication rule.
 */
abstract class MutableObjectSet<T>(vararg elements: T) : HashSet<T>() {
    init {
        addAll(elements)
    }

    abstract fun isDuplicate(a: T, b: T): Boolean

    override fun add(element: T): Boolean {
        for (e in this)
            if (isDuplicate(element, e)) return false

        return super.add(element)
    }
}