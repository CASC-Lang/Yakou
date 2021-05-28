package org.casclang.casc.parsing.scope

data class QualifiedName(val qualifiedPath: String, val reference: String) {
    val qualifiedName = "${if (qualifiedPath.isEmpty()) "" else "$qualifiedPath."}$reference"

    fun removeDuplicate(qualifiedPath: String) =
        qualifiedName.replace(qualifiedPath, "").removePrefix(".")
}
