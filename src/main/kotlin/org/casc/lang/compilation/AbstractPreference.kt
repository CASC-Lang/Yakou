package org.casc.lang.compilation

import java.io.File
import java.net.URLClassLoader

abstract class AbstractPreference {
    abstract var enableColor: Boolean
    abstract var classLoader: URLClassLoader?
    var outputDir: File = File(System.getProperty("user.dir"))
        set(value) {
            classLoader = URLClassLoader(arrayOf(value.toURI().toURL()))
            field = value
        }
    abstract var compileAndRun: Boolean
}