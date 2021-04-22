package io.github.chaosunity.casc.parsing.global

class CompilationUnit(private var _classDeclaration: ClassDeclaration) {
    def classDeclaration: ClassDeclaration = _classDeclaration

    def className: String = _classDeclaration.name
}
