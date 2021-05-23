package io.github.chaosunity.casc.bytecode.expression

import io.github.chaosunity.casc.parsing.node.expression.FieldReference
import io.github.chaosunity.casc.parsing.node.expression.LocalVariableReference
import io.github.chaosunity.casc.parsing.scope.CallingScope
import io.github.chaosunity.casc.parsing.scope.Scope
import io.github.chaosunity.casc.parsing.type.ArrayType
import jdk.internal.org.objectweb.asm.MethodVisitor
import jdk.internal.org.objectweb.asm.Opcodes.*

class ReferenceFactory(private val mv: MethodVisitor, private val ef: ExpressionFactory, private val scope: Scope) {
    fun generate(local: LocalVariableReference) {
        val varName = local.name
        val index = scope.getLocalVariableIndex(varName)
        val type = local.type

        mv.visitVarInsn(type.loadVariableOpcode, index)

        if (local.dimensions.isNotEmpty()) {
            if (type !is ArrayType)
                throw RuntimeException("Type $type cannot be indexed.")

            val actualDimension = type.dimension - local.dimensions.size
            val actualType = if (actualDimension == 0) type.baseType
            else ArrayType(type.baseType, actualDimension)
            val dimensions = local.dimensions.toMutableList()
            val lastDimension = dimensions.removeLast()

            dimensions.forEach {
                it.accept(ef)
                mv.visitInsn(AALOAD)
            }

            lastDimension.accept(ef)
            mv.visitInsn(actualType.arrayLoadOpcode)
        }
    }

    fun generate(field: FieldReference) {
        val varName = field.name
        val type = field.type
        val descriptor = type.descriptor

        if (scope.callingScope == CallingScope.OBJECT) {
            mv.visitVarInsn(ALOAD, 0)
            mv.visitFieldInsn(GETFIELD, scope.classType.internalName, varName, descriptor)
        } else {
            mv.visitFieldInsn(GETSTATIC, scope.classType.internalName, varName, descriptor)
        }

        if (field.dimensions.isNotEmpty()) {
            if (type !is ArrayType)
                throw RuntimeException("Type $type cannot be indexed.")

            val actualDimension = type.dimension - field.dimensions.size
            val actualType = if (actualDimension == 0) type.baseType
            else ArrayType(type.baseType, actualDimension)
            val dimensions = field.dimensions.toMutableList()
            val lastDimension = dimensions.removeLast()

            dimensions.forEach {
                it.accept(ef)
                mv.visitInsn(AALOAD)
            }

            lastDimension.accept(ef)
            mv.visitInsn(actualType.arrayLoadOpcode)
        }
    }
}