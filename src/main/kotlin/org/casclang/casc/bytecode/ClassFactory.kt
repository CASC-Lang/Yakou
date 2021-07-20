package org.casclang.casc.bytecode

import org.casclang.casc.parsing.ClassDeclaration
import jdk.internal.org.objectweb.asm.ClassWriter
import jdk.internal.org.objectweb.asm.Opcodes.ACC_SUPER

class ClassFactory(private val qualifiedClassName: String) {
    companion object {
        const val CLASS_VERSION = 52
    }

    private val cw = ClassWriter(ClassWriter.COMPUTE_FRAMES + ClassWriter.COMPUTE_MAXS)

    fun generate(classDeclaration: ClassDeclaration): ClassWriter {
        val methods = classDeclaration.functions
        val fields = classDeclaration.fields
        val mf = FunctionFactory(cw, classDeclaration.implementations)
        val ff = FieldFactory(cw)

        cw.visit(
            CLASS_VERSION,
            classDeclaration.accessModifier.accessOpcode + ACC_SUPER,
            qualifiedClassName,
            null,
            classDeclaration.scope.superClassInternalName,
            null
        )

        fields.forEach(ff::generate)
        methods.forEach(mf::generate)

        cw.visitEnd()

        return cw
    }
}