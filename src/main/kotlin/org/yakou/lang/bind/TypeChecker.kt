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

        return BoundResult.IMPOSSIBLE
    }

    // Used for representing different behaviour of casting
    enum class BoundResult {
        SAME,               // types are same
        IMPOSSIBLE,         // types are unrelated, but we still allowed it exists anyway
        SUBCLASS,           // types are related, source type is able to cast into target type
        CAST,               // types are related, source type is able to promote into target type (primitive class only)
        BOX,                // types are related, target type is source type's boxed class variant, box source type into target type
        UNBOX,              // types are related, target type is source type's boxed class variant, unbox source type from target type
        FAIL                // types are unrelated, one of types is primitive type meanwhile the other type is not primitive
    }
}