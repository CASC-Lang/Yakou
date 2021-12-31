package org.casc.lang.compilation

import java.io.File
import java.net.URLClassLoader

object Preference {
    var enableColor = true
    var classLoader: URLClassLoader? = null
        private set
    var outputDir: File = File(System.getProperty("user.dir"))
        set(value) {
            classLoader = URLClassLoader(arrayOf(value.toURI().toURL()))
            field = value
        }
    var compileAndRun = false
}