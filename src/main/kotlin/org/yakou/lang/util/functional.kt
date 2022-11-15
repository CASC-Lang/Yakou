package org.yakou.lang.util

@Suppress("UNCHECKED_CAST")
fun <E, T> E.`as`(): T
        where T : E =
    this as T

@Suppress("UNCHECKED_CAST")
fun <E, T> List<E>.mapAs(): List<T>
        where T : E =
    this.map { it as T }
