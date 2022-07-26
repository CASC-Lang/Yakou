package org.yakou.lang.bind

// Yakou permits no implicit type conversion, only lower-bound implicit type conversion is allowed by default
object TypeChecker {
    fun canImplicitCast(table: Table, from: TypeInfo, to: TypeInfo): BoundResult =
        if (from == to) BoundResult.SAME
        else if (from is TypeInfo.Class && to is TypeInfo.Class) isSubClass(table, from, to)
        else BoundResult.FAIL

    fun canExplicitCast(table: Table, from: TypeInfo, to: TypeInfo): BoundResult {
        if (from is TypeInfo.Primitive && to is TypeInfo.Primitive) {
            return if (from.type.convertable() && to.type.convertable()) {
                if (from.type == to.type) BoundResult.SAME else BoundResult.CAST
            } else {
                BoundResult.FAIL
            }
        }

        if (from is TypeInfo.Class && to is TypeInfo.Primitive) {
            return if (from.canonicalName == to.type.wrappedJvmClazz.canonicalName) BoundResult.UNBOX else BoundResult.FAIL
        }

        if (from is TypeInfo.Primitive && to is TypeInfo.Class) {
            return if (from.type.wrappedJvmClazz.canonicalName == to.canonicalName) BoundResult.BOX else BoundResult.FAIL
        }

        if (from is TypeInfo.Class && to is TypeInfo.Class) {
            return isSubClass(table, from, to)
        }

        return BoundResult.FAIL
    }

    private fun isSubClass(table: Table, from: TypeInfo.Class, to: TypeInfo.Class): BoundResult {
        var currentSuperClass: TypeInfo.Class? = from

        while (currentSuperClass != null) {
            if (currentSuperClass == to) {
                return if (currentSuperClass == from) BoundResult.SAME else BoundResult.SUBCLASS
            }

            currentSuperClass = currentSuperClass.superClassType
        }

        return BoundResult.FAIL
    }

    enum class BoundResult {
        SAME, SUBCLASS, CAST, BOX, UNBOX, FAIL
    }
}