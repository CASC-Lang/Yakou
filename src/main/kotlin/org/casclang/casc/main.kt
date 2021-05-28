package org.casclang.casc

import org.casclang.casc.compilation.Compiler

internal fun main(args: Array<String>) {
    try {
        Compiler.init(args).compile()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}