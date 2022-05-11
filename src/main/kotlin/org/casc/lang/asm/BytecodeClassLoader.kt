package org.casc.lang.asm

import java.net.URL
import java.net.URLClassLoader

class BytecodeClassLoader(urls: Array<URL?>?, parent: ClassLoader?) : URLClassLoader(urls, parent) {
    fun defineClass(className: String?, bytecode: ByteArray): Class<*> {
        try {
            return loadClass(className)
        } catch (_: ClassNotFoundException) {
        }
        return defineClass(className, bytecode, 0, bytecode.size)
    }
}