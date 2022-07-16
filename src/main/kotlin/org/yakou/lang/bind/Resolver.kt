package org.yakou.lang.bind

import org.yakou.lang.ast.Path

class Resolver(private val scope: Scope) {
    fun resolveIdentifier(currentPackagePath: Path.SimplePath, currentClassPath: Path.SimplePath, name: String): Symbol? {
        return resolveLocalVariable(currentPackagePath, currentClassPath, name)
    }

    private fun resolveLocalVariable(currentPackagePath: Path.SimplePath, currentClassPath: Path.SimplePath, name: String): Symbol? {
        return scope.variables.find { it.name == name } ?: resolveOwnerClassField(currentPackagePath, currentClassPath, name)
    }

    private fun resolveOwnerClassField(currentPackagePath: Path.SimplePath, currentClassPath: Path.SimplePath, name: String): Symbol? {
        return scope.table.findClassMember(qualifyPath(currentPackagePath, currentClassPath), ClassMember.MemberType.FIELD, name)
    }

    private fun qualifyPath(packagePath: Path.SimplePath, classPath: Path.SimplePath): String {
        val packagePathString = packagePath.toString()
        val classPathString = classPath.toString().ifBlank { "PackageYk" }

        return (if (packagePathString.isBlank()) "" else "$packagePathString::") + classPathString
    }
}