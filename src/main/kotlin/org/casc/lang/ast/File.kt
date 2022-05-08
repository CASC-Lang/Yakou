package org.casc.lang.ast

data class File(val path: String, val relativeFilePath: String, var typeInstance: TypeInstance) {
    val fileName: String by lazy {
        relativeFilePath.split('\\', '/').last()
    }
}
