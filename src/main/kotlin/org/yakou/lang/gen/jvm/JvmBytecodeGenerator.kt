package org.yakou.lang.gen.jvm

import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes
import org.yakou.lang.ast.Item
import org.yakou.lang.ast.YkFile
import org.yakou.lang.bind.TypeInfo
import org.yakou.lang.compilation.CompilationSession
import org.yakou.lang.compilation.CompilationUnit
import java.io.File

class JvmBytecodeGenerator(private val compilationSession: CompilationSession) {
    private val classWriters: HashMap<TypeInfo.Class, ClassWriter> = hashMapOf()

    fun gen(compilationUnit: CompilationUnit) {
        genYkFile(compilationUnit.ykFile!!)
    }

    fun finalize() {
        for ((classTypeInfo, classWriter) in classWriters) {
            val bytecode = classWriter.toByteArray()
            val classFile = File(
                compilationSession.preference.outputFolder,
                "${classTypeInfo.standardTypePath.replace("::", "/")}.class"
            )

            if (!classFile.parentFile.exists()) {
                classFile.parentFile.mkdirs()
            }

            if (!classFile.exists()) {
                classFile.createNewFile()
            }

            classFile.writeBytes(bytecode)
        }
    }

    private fun genYkFile(ykFile: YkFile) {
        for (item in ykFile.items)
            genItem(item)
    }

    private fun genItem(item: Item) {
        when (item) {
            is Item.Package -> {
                if (item.items != null)
                    for (innerItem in item.items)
                        genItem(innerItem)
            }
            is Item.Const -> genConst(item)
            is Item.Function -> genFunction(item)
        }
    }

    private fun genConst(const: Item.Const) {
        TODO()
    }

    private fun genFunction(function: Item.Function) {
        val classWriter = getClassWriter(function.functionInstance.ownerTypeInfo)
        val methodVisitor = classWriter.visitMethod(
            function.functionInstance.access,
            function.functionInstance.name,
            function.functionInstance.descriptor,
            null,
            null
        )

        // TODO: Implement instructions

        methodVisitor.visitEnd()
    }

    private fun getClassWriter(clazz: TypeInfo.Class): ClassWriter =
        classWriters[clazz] ?: run {
            val classWriter = ClassWriter(ClassWriter.COMPUTE_FRAMES + ClassWriter.COMPUTE_MAXS)

            classWriters[clazz] = classWriter

            classWriter.visit(
                Opcodes.V17,
                clazz.access + Opcodes.ACC_SUPER,
                clazz.internalName,
                null, // TODO: Implement generic
                clazz.superClassType?.internalName.orEmpty(),
                clazz.interfaceTypes.map(TypeInfo.Class::internalName).toTypedArray()
            )

            classWriter
        }
}