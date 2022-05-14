package org.casc.lang.asm

import org.objectweb.asm.ClassWriter

class CommonClassWriter(flags: Int, private val classLoader: ClassLoader) : ClassWriter(flags) {
    override fun getClassLoader(): ClassLoader {
        return classLoader
    }

    override fun getCommonSuperClass(type1: String?, type2: String?): String {
        // search common type from Table

        return super.getCommonSuperClass(type1, type2)
    }
}