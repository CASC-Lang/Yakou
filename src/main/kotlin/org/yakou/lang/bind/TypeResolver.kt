package org.yakou.lang.bind

import org.yakou.lang.ast.Type

class TypeResolver(private val scope: Scope?, private val table: Table) {
    fun resolveType(type: Type) =
        resolveTypeVariable(type)

    private fun resolveTypeVariable(type: Type): TypeInfo? =
        scope?.typeVariables?.get(type.standardizeType()) ?: resolveTypeFromTable(type)

    private fun resolveTypeFromTable(type: Type): TypeInfo? =
        table.findType(type)
}