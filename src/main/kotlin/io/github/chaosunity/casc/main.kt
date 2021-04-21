package io.github.chaosunity.casc

import io.github.chaosunity.antlr.CASCLexer
import io.github.chaosunity.casc.bytecodegen.instructions.BytecodeGenerator
import io.github.chaosunity.casc.parse.SyntaxTreeTraverser
import java.io.File


fun main(args: Array<String>) {
    val CASCFile = File(args[0])
    val fileName = CASCFile.name
    val fileAbsolutePath = CASCFile.absolutePath
    val className = fileName.removeSuffix(".casc")
    val instructionsQueue = SyntaxTreeTraverser().getInstructions(fileAbsolutePath)
    val bytecode = BytecodeGenerator().generateBytecode(instructionsQueue, className)

    saveBytecode(fileAbsolutePath, bytecode)
}

fun saveBytecode(fileName: String, bytecode: ByteArray) {
    val clazzName = fileName.replace(".casc", ".class")
    File(clazzName).writeBytes(bytecode)
}