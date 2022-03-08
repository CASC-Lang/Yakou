package org.casc.lang.utils

fun <T, R> List<T>.call(call: () -> R): List<T> {
    call()
    return this
}