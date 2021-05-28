package org.casclang.casc.parsing

import org.casclang.casc.compilation.PackageTree

data class CompilationUnit(val modulePath: String?, val classDeclaration: ClassDeclaration) {
    val className = classDeclaration.name
    val qualifiedClassName = if (modulePath != null) "${modulePath.replace('.', '/')}/$className"
    else className

    init {
        if (!PackageTree.classes.containsKey(qualifiedClassName))
            throw IllegalArgumentException("Module name mismatched.")
    }
}
