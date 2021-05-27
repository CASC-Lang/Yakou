package io.github.chaosunity.casc.bytecode

import io.github.chaosunity.casc.parsing.CompilationUnit

class BytecodeFactory {
    fun generate(compilationUnit: CompilationUnit): ByteArray {
        val classDeclaration = compilationUnit.classDeclaration
        val cf = ClassFactory(compilationUnit.qualifiedClassName)

        return cf.generate(classDeclaration).toByteArray()
    }
}