package org.casc.lang.compilation

import org.casc.lang.asm.BytecodeClassLoader
import java.io.File
import java.net.URLClassLoader

object GlobalPreference : AbstractPreference() {
    override var enableColor: Boolean = true
    override var sourceFile: File? = null
    override var enableTiming: Boolean = false
    override var compileAndRun: Boolean = false
    override var noEmit: Boolean = false

    fun reset() {
        enableColor = true
        outputDir = File(System.getProperty("user.dir"))
        enableTiming = false
        compileAndRun = false
        noEmit = false
    }
}