package io.github.chaosunity.casc.bytecode

import io.github.chaosunity.casc.compilation.Compiler
import io.github.chaosunity.casc.compilation.PackageTree
import io.github.chaosunity.casc.parsing.ClassDeclaration
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
        val mf = MethodFactory(cw)
        val ff = FieldFactory(cw)

        cw.visit(
            CLASS_VERSION,
            classDeclaration.accessModifier.accessOpcode + ACC_SUPER,
            qualifiedClassName,
            null,
            "java/lang/Object",
            null
        )

        fields.forEach(ff::generate)
        methods.forEach(mf::generate)

        cw.visitEnd()

        return cw
    }
}