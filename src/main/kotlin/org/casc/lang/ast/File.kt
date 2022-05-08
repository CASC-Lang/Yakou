package org.casc.lang.ast

import org.casc.lang.table.Reference

data class File(val path: String, val relativeFilePath: String, val usages: List<Reference>, var typeInstance: TypeInstance) {
    val fileName: String by lazy {
        relativeFilePath.split('\\', '/').last()
    }
}
