package org.yakou.lang.bind

import kotlin.collections.LinkedHashSet

// Yakou permits no implicit type conversion, only lower-bound implicit type conversion is allowed by default
object TypeChecker {
    fun canImplicitCast(from: TypeInfo, to: TypeInfo): BoundResult =
        if (from == to) BoundResult.SAME
        else if (from is TypeInfo.Class && to is TypeInfo.Class) isSubClass(from, to)
        else BoundResult.FAIL

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

        if (from is TypeInfo.Class && to is TypeInfo.Class) {
            return isSubClass(from, to)
        }

        return BoundResult.FAIL
    }

    private fun isSubClass(from: TypeInfo.Class, to: TypeInfo.Class): BoundResult {
        if (from == to)
            return BoundResult.SAME

        val castingIterator = classAsQueue(from).iterator()

        while (castingIterator.hasNext()) {
            val currentClass = castingIterator.next()

            if (currentClass == from)
                return BoundResult.SUBCLASS
        }

        return BoundResult.IMPOSSIBLE
    }

    // Construct inheritance tree from clazz into linked list
    private fun classAsQueue(clazz: TypeInfo.Class): LinkedHashSet<TypeInfo.Class> {
        val queue = LinkedHashSet<TypeInfo.Class>()

        appendToQueue(queue, clazz)
        queue.add(TypeInfo.Class.OBJECT_TYPE_INFO)

        return queue
    }

    private fun appendToQueue(queue: LinkedHashSet<TypeInfo.Class>, clazz: TypeInfo.Class) {
        if (clazz == TypeInfo.Class.OBJECT_TYPE_INFO)
            return

        queue.add(clazz)

        for (trait in clazz.interfaceTypes) {
            appendToQueue(queue, trait)
        }

        if (clazz.superClassType != null)
            appendToQueue(queue, clazz.superClassType)
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