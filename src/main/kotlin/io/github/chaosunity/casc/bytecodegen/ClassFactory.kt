package io.github.chaosunity.casc.bytecodegen

import io.github.chaosunity.casc.parsing.global.ClassDeclaration
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes

class ClassFactory {
    companion object {
        const val CLASS_VERSION = 52
    }

    private val cw = ClassWriter(ClassWriter.COMPUTE_FRAMES + ClassWriter.COMPUTE_MAXS)

    fun generate(classDeclaration: ClassDeclaration): ClassWriter {
        val className = classDeclaration.name()
        val methods = classDeclaration.methods()

        cw.visit(CLASS_VERSION, Opcodes.ACC_PUBLIC + Opcodes.ACC_SUPER, className, null, "java/lang/Object", null)

        run {
            methods.forEach { MethodFactory(cw).generate(it) }
        }

        cw.visitEnd()

        return cw
    }
}