package io.github.chaosunity.casc.bytecodegen

import io.github.chaosunity.casc.parsing.global.CompilationUnit

class BytecodeFactory {
    fun generate(compilationUnit: CompilationUnit): ByteArray {
        val classDeclaration = compilationUnit.classDeclaration()
        val cf = ClassFactory()

        return cf.generate(classDeclaration).toByteArray()
    }
}