package org.yakou.lang.bind

// Yakou permits no implicit type conversion, only lower-bound implicit type conversion is allowed by default
object TypeChecker {
    fun canImplicitCast(from: TypeInfo, to: TypeInfo): BoundResult =
        if (from == to) BoundResult.SAME else BoundResult.FAIL

    fun canExplicitCast(from: TypeInfo, to: TypeInfo): BoundResult {
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

        // TODO: CHECK CLASS INHERITANCE RELATIVE TREE

        return BoundResult.FAIL
    }

    enum class BoundResult {
        SAME, CAST, BOX, UNBOX, FAIL
    }
}