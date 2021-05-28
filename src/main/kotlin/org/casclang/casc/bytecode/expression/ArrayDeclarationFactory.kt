package org.casclang.casc.bytecode.expression

import org.casclang.casc.parsing.node.expression.ArrayDeclaration
import org.casclang.casc.parsing.type.BuiltInType
import jdk.internal.org.objectweb.asm.MethodVisitor
import jdk.internal.org.objectweb.asm.Opcodes.*

class ArrayDeclarationFactory(private val mv: MethodVisitor, private val ef: ExpressionFactory) {
    fun generate(arrayDeclaration: ArrayDeclaration) {
        val baseType = arrayDeclaration.baseType

        arrayDeclaration.expressions.forEach(ef::generate)

        if (arrayDeclaration.dimension == 1) {
            if (baseType.isBuiltInType()) {
                mv.visitIntInsn(NEWARRAY, (baseType as BuiltInType).typeOpcode)
            } else {
                mv.visitTypeInsn(ANEWARRAY, baseType.internalName)
            }
        } else {
            mv.visitMultiANewArrayInsn(arrayDeclaration.type.descriptor, arrayDeclaration.expressions.size)
        }
    }
}