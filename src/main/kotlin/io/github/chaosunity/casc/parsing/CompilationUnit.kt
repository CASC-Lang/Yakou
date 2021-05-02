package io.github.chaosunity.casc.parsing

data class CompilationUnit(val classDeclaration: ClassDeclaration) {
    val className = classDeclaration.name
}
