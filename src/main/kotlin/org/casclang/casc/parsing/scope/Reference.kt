package org.casclang.casc.parsing.scope

data class Reference(private val path: String, var localName: String = "") {
    var fullPath = path.replace("::", ".")

    init {
        if (localName.isEmpty())
            localName = fullPath.split(".").last()
    }
}
