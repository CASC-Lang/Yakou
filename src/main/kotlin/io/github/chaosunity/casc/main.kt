package io.github.chaosunity.casc

internal fun main(args: Array<String>) {
    try {
        Compiler().compile(args)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}