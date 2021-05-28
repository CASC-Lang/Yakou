package org.casclang.casc.bytecode

import org.casclang.casc.parsing.CompilationUnit

class BytecodeFactory {
    fun generate(compilationUnit: CompilationUnit): ByteArray {
        val classDeclaration = compilationUnit.classDeclaration
        val cf = ClassFactory(compilationUnit.qualifiedClassName)

        return cf.generate(classDeclaration).toByteArray()
    }
}