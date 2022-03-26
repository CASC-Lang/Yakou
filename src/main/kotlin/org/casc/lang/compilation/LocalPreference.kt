package org.casc.lang.compilation

import java.io.File

data class LocalPreference(
    override var enableColor: Boolean = true,
    override var sourceFile: File? = null,
    override var enableTiming: Boolean = false,
    override var compileAndRun: Boolean = false,
    override var noEmit: Boolean = false
) : AbstractPreference()
