package org.casclang.casc.parsing

data class CompilationUnit(val modulePath: String?, val classDeclaration: ClassDeclaration) {
    val className = classDeclaration.name
    val qualifiedClassName = if (modulePath != null) "${modulePath.replace(".", "/")}/$className"
    else className
}
