package org.casc.lang.ast

object DisplayFactory {
    fun getParametersTypePretty(parameters: List<Parameter>): String =
        parameters.mapNotNull { it.typeReference?.fullQualifiedPath }.joinToString()
}