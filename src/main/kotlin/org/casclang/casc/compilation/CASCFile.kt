package org.casclang.casc.compilation

data class CASCFile(val name: String, val packagePath: String, val extensionName: String, var isCompiled: Boolean = false) {
    val relativeFilePath = "${packagePath.replace('.', '/')}/$name.$extensionName"
    val absoluteFilePath = "${Compiler.compilation.source.path}/${relativeFilePath}"
}
