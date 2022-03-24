package org.casc.lang.asm

import java.net.URLClassLoader
import java.lang.ClassNotFoundException
import java.net.URL

class BytecodeClassLoader(urls: Array<URL?>?, parent: ClassLoader?) : URLClassLoader(urls, parent) {
    fun defineClass(className: String?, bytecode: ByteArray): Class<*> {
        try {
            return loadClass(className)
        } catch (_: ClassNotFoundException) {
        }
        return defineClass(className, bytecode, 0, bytecode.size)
    }
}