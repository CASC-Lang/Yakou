package org.yakou.lang.bind

import org.yakou.lang.ast.Type
import java.util.*

/**
 * Table class stores type infos and function infos in Yakou standard type format.
 * Example standard type formats:
 * - pkgA::pkgB::class   (Normal type or just package path)
 * - [pkgA::pkgB::class] (Array type)
 */
class Table {
    private val classMemberTable: MutableMap<String, EnumMap<ClassMember.MemberType, MutableList<ClassMember>>> = hashMapOf()
    private val typeTable: MutableMap<String, TypeInfo> = hashMapOf()

    fun registerClassMember(classMember: ClassMember, packageLevel: Boolean): Boolean {
        val qualifiedOwnerPath = classMember.qualifiedOwnerPath
        val ownerTypeInfo = typeTable.getOrPut(qualifiedOwnerPath) {
            if (packageLevel) {
                TypeInfo.PackageClass(classMember.packagePath)
            } else {
                TODO()
            }
        } as TypeInfo.Class

        return if (!classMemberTable.containsKey(qualifiedOwnerPath)) {
            // Create a new set of functions
            classMemberTable[qualifiedOwnerPath] = EnumMap(ClassMember.MemberType::class.java)

            for (type in ClassMember.MemberType.values) {
                if (type == classMember.memberType) {
                    classMemberTable[qualifiedOwnerPath]!![type] = mutableListOf(classMember)
                } else {
                    classMemberTable[qualifiedOwnerPath]!![type] = mutableListOf()
                }
            }

            classMember.ownerTypeInfo = ownerTypeInfo

            true
        } else if (classMemberTable[qualifiedOwnerPath]?.get(classMember.memberType)!!.any { it == classMember }) false
        else {
            classMemberTable[qualifiedOwnerPath]!![classMember.memberType]!! += classMember

            classMember.ownerTypeInfo = ownerTypeInfo

            true
        }
    }

    fun findType(type: Type): TypeInfo? {
        val typeName = type.standardizeType()

        PrimitiveType.findPrimitiveType(typeName)?.let {
            return TypeInfo.Primitive(it)
        }

        if (typeName.startsWith("[")) {
            // Array type
            val arrayTypeInfo = asTypeInfo(type)

            // Check if base type is type path, and validate if type path exists
            return when ((arrayTypeInfo as TypeInfo.Array).baseType) {
                is TypeInfo.Primitive, is TypeInfo.Class -> arrayTypeInfo
                is TypeInfo.Array -> null // Unreachable
            }
        }

        return typeTable[typeName]
    }

    private fun asTypeInfo(type: Type): TypeInfo? = when (type) {
        is Type.Array -> {
            var finalArrayType: TypeInfo.Array
            var dimensionCount = 0
            var innerType = type.type

            while (innerType is Type.Array) {
                dimensionCount++
                innerType = innerType.type
            }

            val baseType = asTypeInfo(innerType)

            if (baseType != null) {
                finalArrayType = TypeInfo.Array(baseType)

                while (dimensionCount > 0) {
                    finalArrayType = TypeInfo.Array(finalArrayType)
                    dimensionCount--
                }

                finalArrayType
            } else null
        }
        is Type.TypePath -> {
            val typeName = type.standardizeType()

            PrimitiveType.findPrimitiveType(typeName)?.let {
                TypeInfo.Primitive(it)
            } ?: typeTable[typeName] ?: run {
                try {
                    val clazz = Class.forName(typeName.replace("::", "."))

                    // After assignment, we can determine the class actually exists in current JVM session, thus we
                    // cache it and return it
                    val typeInfo = TypeInfo.fromClass(clazz)
                    typeTable[typeName] = typeInfo

                    return@run typeInfo
                } catch (e: Exception) {
                    e.printStackTrace()
                } catch (e: Error) {
                    e.printStackTrace()
                }

                null
            }
        }
    }
}