package org.yakou.lang.ast

interface Declaration {
    val packagePath: String
    val classPath: String
    val qualifiedPath: String
        get() = if (packagePath.isBlank()) classPath else "$packagePath::$classPath"
}