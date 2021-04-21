package io.github.chaosunity.casc.bytecodegen.instructions

import org.objectweb.asm.MethodVisitor

interface Instruction {
    fun apply(mv: MethodVisitor)
}