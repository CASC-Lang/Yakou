package org.casclang.casc.parsing.scope

data class Usage(val fullPath: String) {
    val classReference: String = fullPath.split(".").last()
}
