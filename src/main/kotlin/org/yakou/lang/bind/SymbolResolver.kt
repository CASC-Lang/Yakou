package org.yakou.lang.bind

import org.yakou.lang.ast.Path

class SymbolResolver(private val scope: Scope) {
    fun resolveFunction(
        currentPackagePath: Path.SimplePath,
        currentClassPath: Path.SimplePath,
        name: String,
        arguments: List<TypeInfo>
    ): Symbol? {
        return resolveOwnerClassFunction(currentPackagePath, currentClassPath, name, arguments)
    }

    private fun resolveOwnerClassFunction(
        currentPackagePath: Path.SimplePath,
        currentClassPath: Path.SimplePath,
        name: String,
        arguments: List<TypeInfo>
    ): Symbol? {
        return scope.table.findFunction(
            currentPackagePath.appendPath(currentClassPath),
            name,
            arguments
        )
    }

    fun resolveIdentifier(
        currentPackagePath: Path.SimplePath,
        currentClassPath: Path.SimplePath,
        name: String
    ): Symbol? {
        return resolveLocalVariable(currentPackagePath, currentClassPath, name)
    }

    private fun resolveLocalVariable(
        currentPackagePath: Path.SimplePath,
        currentClassPath: Path.SimplePath,
        name: String
    ): Symbol? {
        return scope.variables.find { it.name == name } ?: resolveOwnerClassField(
            currentPackagePath,
            currentClassPath,
            name
        )
    }

    private fun resolveOwnerClassField(
        currentPackagePath: Path.SimplePath,
        currentClassPath: Path.SimplePath,
        name: String
    ): Symbol? {
        val field = scope.table.findField(
            currentPackagePath.appendPath(currentClassPath),
            name
        )

        return if (field is ClassMember.Field && field.isStatic) field else null
    }

    private fun Path.SimplePath.appendPath(path: Path.SimplePath): String =
        this.toString().appendPath(path.toString())

    private fun String.appendPath(path: String): String =
        if (this.isEmpty()) path
        else "$this::$path"
}