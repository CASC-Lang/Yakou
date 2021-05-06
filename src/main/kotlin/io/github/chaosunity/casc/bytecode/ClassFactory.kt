package io.github.chaosunity.casc.bytecode

import io.github.chaosunity.casc.parsing.ClassDeclaration
import io.github.chaosunity.casc.parsing.Constructor
import jdk.internal.org.objectweb.asm.ClassWriter
import jdk.internal.org.objectweb.asm.Opcodes.*

class ClassFactory {
    companion object {
        const val CLASS_VERSION = 52
    }

    private val cw = ClassWriter(ClassWriter.COMPUTE_FRAMES + ClassWriter.COMPUTE_MAXS)

    fun generate(classDeclaration: ClassDeclaration): ClassWriter {
        val className = classDeclaration.name
        val methods = classDeclaration.functions
        val mf = MethodFactory(cw)

        cw.visit(CLASS_VERSION, ACC_PUBLIC + ACC_SUPER, className, null, "java/lang/Object", null)

        methods.forEach { mf.generate(it) }

        cw.visitEnd()

        return cw
    }
}