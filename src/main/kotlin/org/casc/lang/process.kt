package org.casc.lang

import org.casc.lang.compilation.Compilation
import java.io.File

fun main(args: Array<String>) {
    val command = args[0]

    when (command) {
        // TODO: Commands
    }

    // File / Folder Compilation
    val file = File(command)

    if (file.exists()) {
        val compilation = Compilation(file)
        compilation.compile()
    } else {
        // TODO: ERROR HANDLING
    }
}
