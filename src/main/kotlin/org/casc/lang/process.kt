package org.casc.lang

import org.casc.lang.compilation.Compilation
import org.casc.lang.compilation.GlobalPreference
import java.io.File

suspend fun main(args: Array<String>) {
    var hasCommand = false
    val command = args[0]

    when (command) {
        "run" -> {
            hasCommand = true
            GlobalPreference.compileAndRun = true
        }
        // TODO: Commands
    }

    // File / Folder Compilation
    val file = File(if (hasCommand) args[1] else command)

    if (file.exists()) {
        val compilation = Compilation(file)
        compilation.compile()
    } else {
        // TODO: ERROR HANDLING
    }
}
