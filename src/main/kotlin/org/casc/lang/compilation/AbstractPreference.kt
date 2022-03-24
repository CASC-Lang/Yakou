package org.casc.lang.compilation

import org.casc.lang.asm.BytecodeClassLoader
import java.io.File
import java.net.URLClassLoader

abstract class AbstractPreference {
    abstract var enableColor: Boolean
    abstract var classLoader: BytecodeClassLoader?
    var outputDir: File = File(System.getProperty("user.dir"))
        set(value) {
            classLoader = BytecodeClassLoader(arrayOf(value.toURI().toURL()), ClassLoader.getSystemClassLoader())
            field = value
        }
    abstract var compileAndRun: Boolean
}