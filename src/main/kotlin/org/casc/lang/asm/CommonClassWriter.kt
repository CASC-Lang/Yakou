package org.casc.lang.asm

import org.objectweb.asm.ClassWriter

class CommonClassWriter(flags: Int, private val classLoader: ClassLoader) : ClassWriter(flags) {
    override fun getClassLoader(): ClassLoader {
        return classLoader
    }
}