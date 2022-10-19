package org.yakou.lang

import org.yakou.lang.cli.ArgumentProcessor
import org.yakou.lang.compilation.CompilationSession

fun main(args: Array<String>) {
    val preference = ArgumentProcessor().processPreference()
    val session = CompilationSession(preference)
    session.compile()
}
