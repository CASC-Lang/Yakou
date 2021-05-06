package io.github.chaosunity.casc

import io.github.chaosunity.casc.compilation.Compiler

internal fun main(args: Array<String>) {
    try {
        Compiler().compile(args)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}