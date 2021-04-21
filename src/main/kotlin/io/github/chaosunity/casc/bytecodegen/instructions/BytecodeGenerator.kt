package io.github.chaosunity.casc.bytecodegen.instructions

import org.objectweb.asm.ClassWriter
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Opcodes
import org.objectweb.asm.Opcodes.*

class BytecodeGenerator : Opcodes {
    fun generateBytecode(instructions: ArrayDeque<Instruction>, name: String): ByteArray {
        val cw = ClassWriter(0)
        var mv: MethodVisitor

        cw.visit(52, ACC_PUBLIC + ACC_SUPER, name, null, "java/lang/Object", null);
        run {
            mv = cw.visitMethod(ACC_PUBLIC + ACC_STATIC, "main", "([Ljava/lang/String;)V", null, null)
            val localVariableCount = instructions.filterIsInstance(VariableDeclaration::class.java).count()
            val maxStack = 100

            instructions.forEach {
                it.apply(mv)
            }

            mv.visitInsn(RETURN)

            mv.visitMaxs(maxStack, localVariableCount)
            mv.visitEnd()
        }

        cw.visitEnd()

        return cw.toByteArray()
    }
}