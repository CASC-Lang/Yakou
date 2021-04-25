package io.github.chaosunity.casc.parsing.global

class CompilationUnit(val classDeclaration: ClassDeclaration) {
    def className: String = classDeclaration.name
}
