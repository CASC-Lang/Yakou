package org.yakou.lang.bind

import org.yakou.lang.ast.Declaration

data class Cls(
    override val packagePath: String,
    override val classPath: String,
    val superClassQualifiedPath: String,
    val interfaceQualifiedPaths: List<String>
) : Declaration {
}