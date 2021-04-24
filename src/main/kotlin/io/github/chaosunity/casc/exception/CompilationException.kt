package io.github.chaosunity.casc.exception

open class CompilationException(msg: String) : RuntimeException(msg) {
    constructor() : this("")
}