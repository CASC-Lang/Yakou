package org.casc.lang.compilation

import java.io.File
import java.net.URLClassLoader

data class LocalPreference(
    override var enableColor: Boolean = true,
    override var classLoader: URLClassLoader? = null,
    override var outputDir: File = File(System.getProperty("user.dir")),
    override var compileAndRun: Boolean = false
) : AbstractPreference()
