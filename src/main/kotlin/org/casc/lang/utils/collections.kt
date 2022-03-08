package org.casc.lang.utils

fun <T, R> List<T>.postCall(call: () -> R): List<T> {
    call()
    return this
}