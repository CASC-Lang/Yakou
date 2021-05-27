package io.github.chaosunity.casc

import io.github.chaosunity.casc.compilation.Compiler

internal fun main(args: Array<String>) {
    try {
        Compiler.init(args).compile()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}