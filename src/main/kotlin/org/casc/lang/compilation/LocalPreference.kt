package org.casc.lang.compilation

import org.casc.lang.asm.BytecodeClassLoader

data class LocalPreference(
    override var enableColor: Boolean = true,
    override var classLoader: BytecodeClassLoader? = null,
    override var compileAndRun: Boolean = false
) : AbstractPreference()
