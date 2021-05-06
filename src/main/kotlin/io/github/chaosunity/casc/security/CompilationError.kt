package io.github.chaosunity.casc.security

sealed class CompilationError(val msg: String) {
    object NO_FILE_PROVIDED : CompilationError("Requires at least one file path to compile with.")
    object BAD_FILE_EXTENSION : CompilationError("File extension must end with .casc or .cas.")
    object SAFE : CompilationError("")
}
