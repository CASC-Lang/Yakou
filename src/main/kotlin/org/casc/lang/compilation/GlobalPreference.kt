package org.casc.lang.compilation

import java.io.File
import java.net.URLClassLoader

object GlobalPreference: AbstractPreference() {
    override var enableColor = true
    override var classLoader: URLClassLoader? = null
    override var outputDir: File = File(System.getProperty("user.dir"))
        set(value) {
            classLoader = URLClassLoader(arrayOf(value.toURI().toURL()))
            field = value
        }
    override var compileAndRun = false

    fun reset() {
        enableColor = true
        classLoader = null
        outputDir = File(System.getProperty("user.dir"))
        compileAndRun = false
    }
}