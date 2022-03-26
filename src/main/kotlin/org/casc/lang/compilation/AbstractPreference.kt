package org.casc.lang.compilation

import org.casc.lang.asm.BytecodeClassLoader
import java.io.File
import java.net.URLClassLoader

abstract class AbstractPreference {
    abstract var enableColor: Boolean
    open var classLoader: BytecodeClassLoader = BytecodeClassLoader(arrayOf(File(System.getProperty("user.dir"), "out").toURI().toURL()), ClassLoader.getSystemClassLoader())
    abstract var sourceFile: File?
    abstract var enableTiming: Boolean
    var outputDir: File = File(System.getProperty("user.dir"), "out")
        set(value) {
            classLoader = BytecodeClassLoader(arrayOf(value.toURI().toURL()), ClassLoader.getSystemClassLoader())
            field = value
        }
    abstract var compileAndRun: Boolean
}