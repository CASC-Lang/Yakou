package org.casc.lang.emitter

import org.casc.lang.ast.Class
import org.casc.lang.ast.File
import org.casc.lang.ast.Function
import org.casc.lang.table.PrimitiveType
import org.casc.lang.table.TypeUtil
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes
import java.io.File as JFile

class Emitter(private val outDir: JFile, private val files: List<File>) {
    fun emit() {
        for (file in files)
            emit(file)
    }

    private fun emit(file: File) {
        val bytecode = emitClass(file.clazz)
        val outFile = JFile(outDir, "/${file.clazz.name!!.literal}.class")

        outFile.writeBytes(bytecode)
    }

    private fun emitClass(clazz: Class): ByteArray {
        val classWriter = ClassWriter(ClassWriter.COMPUTE_FRAMES + ClassWriter.COMPUTE_MAXS)

        classWriter.visit(
            61,
            Opcodes.ACC_PUBLIC + Opcodes.ACC_SUPER,
            clazz.name!!.literal,
            null,
            "java/lang/Object",
            null
        )

        clazz.functions.forEach {
            emitFunction(classWriter, it)
        }

        classWriter.visitEnd()

        return classWriter.toByteArray()
    }

    private fun emitFunction(classWriter: ClassWriter, function: Function) {
        val methodWriter = classWriter.visitMethod(
            function.accessFlag,
            function.name!!.literal,
            function.descriptor,
            null,
            null
        )

        methodWriter.visitCode()

        methodWriter.visitInsn(Opcodes.RETURN)

        methodWriter.visitEnd()
    }
}