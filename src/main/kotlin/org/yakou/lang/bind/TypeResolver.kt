package org.yakou.lang.bind

import org.yakou.lang.ast.Type

class TypeResolver(private val scope: Scope?, private val table: Table) {
    fun resolveType(type: Type): TypeInfo? {
        return if (type is Type.Array) {
            resolveType(type.type)?.let(TypeInfo::Array)
        } else {
            resolveTypeVariable(type)
        }
    }

    private fun resolveTypeVariable(type: Type): TypeInfo? =
        scope?.typeVariables?.get(type.standardizeType()) ?: resolveTypeFromTable(type)

    private fun resolveTypeFromTable(type: Type): TypeInfo? =
        table.findType(type)
}
