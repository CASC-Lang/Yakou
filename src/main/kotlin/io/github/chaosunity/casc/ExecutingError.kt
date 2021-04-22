package io.github.chaosunity.casc

import java.lang.IllegalArgumentException

sealed class ExecutingError(message: String) : IllegalArgumentException(message) {
    object NO_FILE_PROVIDED : ExecutingError("Requires at least one file path to compile with.")
    object BAD_FILE_EXTENSION : ExecutingError("File extension must end with .casc or .cas.")
    object SAFE : ExecutingError("")
}
