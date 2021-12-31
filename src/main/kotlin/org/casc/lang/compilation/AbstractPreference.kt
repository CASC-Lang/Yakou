package org.casc.lang.compilation

import java.io.File
import java.net.URLClassLoader

abstract class AbstractPreference {
    abstract var enableColor: Boolean
    abstract var classLoader: URLClassLoader?
    abstract var outputDir: File
    abstract var compileAndRun: Boolean
}