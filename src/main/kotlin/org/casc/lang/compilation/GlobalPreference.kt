package org.casc.lang.compilation

import java.io.File

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