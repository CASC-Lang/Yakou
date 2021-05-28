package org.casclang.casc.parsing.scope

/**
 *  Qualified path should be in 'module.path.class.name' format.
 */
sealed class Usage(val qualifiedPath: String)

class PathUsage(qualifiedPath: String) : Usage(qualifiedPath)

class ClassUsage(qualifiedPath: String, val className: String) : Usage(qualifiedPath) {
    val qualifiedName = "${if (qualifiedPath.isEmpty()) "" else "$qualifiedPath."}$className"
}