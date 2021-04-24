package io.github.chaosunity.casc.bytecodegen

import io.github.chaosunity.casc.parsing.`class`.Function
import io.github.chaosunity.casc.util.DescriptorFactory
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes.*

class MethodFactory(private val cw: ClassWriter) {
    fun generate(function: Function) {
        val scope = function.scope()
        val functionName = function.name()
        val descriptor = DescriptorFactory.getMethodDescriptor(function)
        val instructions = function.statements()
        val access = ACC_PUBLIC + ACC_STATIC
        val mv = cw.visitMethod(access, functionName, descriptor, null, null)

        mv.visitCode()

        run {
            val sf = StatementFactory(mv)

            instructions.forEach { sf.generate(it, scope) }

            mv.visitInsn(RETURN)
            mv.visitMaxs(-1, -1)
        }

        mv.visitEnd()
    }
}