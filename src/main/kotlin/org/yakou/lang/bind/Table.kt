package org.yakou.lang.bind

import java.util.EnumMap
import org.yakou.lang.ast.GenericDeclarationParameters
import org.yakou.lang.ast.Type
import org.yakou.lang.util.`as`
import org.yakou.lang.util.mapAs

/**
 * Table class stores type infos and function infos in Yakou standard type format.
 * Example standard type formats:
 * - pkgA::pkgB::class   (Normal type or just package path)
 * - [pkgA::pkgB::class] (Array type)
 */
class Table {
    private val typeTable: MutableMap<String, TypeInfo> = hashMapOf()
    private val classMemberTable: MutableMap<String, EnumMap<ClassMember.MemberType, MutableList<ClassMember>>> =
        hashMapOf()

    fun registerClassMember(classMember: ClassMember): Boolean {
        val qualifiedOwnerPath = classMember.qualifiedOwnerPath
        val ownerTypeInfo = typeTable[qualifiedOwnerPath]!! as TypeInfo.Class

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
        } else if (classMemberTable[qualifiedOwnerPath]?.get(classMember.memberType)!!.any { it == classMember }) {
            false
        } else {
            classMemberTable[qualifiedOwnerPath]!![classMember.memberType]!! += classMember

            classMember.ownerTypeInfo = ownerTypeInfo

            true
        }
    }

    fun registerPackageClass(packagePath: String) {
        val qualifiedClassPath = packagePath.appendPath("PackageYk")

        if (!typeTable.containsKey(packagePath)) {
            typeTable[qualifiedClassPath] = TypeInfo.PackageClass(
                packagePath
            )
        }
    }

    fun registerClassType(
        access: Int,
        packagePath: String,
        classPath: String,
        genericDeclarationParameters: List<GenericDeclarationParameters.GenericDeclarationParameter>
    ): TypeInfo.Class? {
        val qualifiedClassPath = packagePath.appendPath(classPath)

        return if (typeTable.containsKey(qualifiedClassPath)) {
            null
        } else {
            val outerClass =
                if (classPath.contains('$')) {
                    val outerClassPath = classPath.substringBeforeLast('$')
                    val qualifiedOuterClassPath = packagePath.appendPath(outerClassPath)

                    findType(qualifiedOuterClassPath)
                } else {
                    null
                }

            val classType = TypeInfo.Class(
                access,
                qualifiedClassPath,
                genericDeclarationParameters.map(GenericDeclarationParameters.GenericDeclarationParameter::genericConstraint),
                outerClass,
                TypeInfo.fromClass(Any::class.java).`as`(),
                listOf()
            )

            typeTable[qualifiedClassPath] = classType

            classType
        }
    }

    fun registerSuperClassType(
        packagePath: String,
        classPath: String,
        superClassType: TypeInfo.Class
    ): Boolean {
        val qualifiedOwnerPath = packagePath.appendPath(classPath)

        return if (!typeTable.containsKey(qualifiedOwnerPath)) {
            false
        } else {
            (typeTable[qualifiedOwnerPath] as TypeInfo.Class).superClassType = superClassType

            true
        }
    }

    // Query functions

    fun getFields(
        qualifiedOwnerPath: String
    ): List<ClassMember.Field>? =
        classMemberTable[qualifiedOwnerPath]
            ?.get(ClassMember.MemberType.FIELD)
            ?.mapAs()

    fun findField(
        qualifiedOwnerPath: String,
        fieldName: String
    ): ClassMember.Field? =
        classMemberTable[qualifiedOwnerPath]
            ?.get(ClassMember.MemberType.FIELD)
            ?.find { it.name == fieldName }
            ?.`as`()

    fun getFunctions(
        qualifiedOwnerPath: String
    ): List<ClassMember.Fn>? =
        classMemberTable[qualifiedOwnerPath]
            ?.get(ClassMember.MemberType.FUNCTION)
            ?.mapAs()

    fun findFunction(
        qualifiedOwnerPath: String,
        functionName: String,
        argumentTypes: List<TypeInfo>
    ): ClassMember.Fn? =
        classMemberTable[qualifiedOwnerPath]
            ?.get(ClassMember.MemberType.FUNCTION)
            ?.mapAs<ClassMember, ClassMember.Fn>()
            ?.filter { it.name == functionName && it.parameterTypeInfos.size == argumentTypes.size }
            ?.find {
                argumentTypes.zip(it.parameterTypeInfos).all { (from, to) ->
                    from.canImplicitCast(to)
                }
            }

    fun getConstructors(
        qualifiedOwnerPath: String
    ): List<ClassMember.Constructor>? =
        classMemberTable[qualifiedOwnerPath]
            ?.get(ClassMember.MemberType.CONSTRUCTOR)
            ?.mapAs()

    fun findConstructor(
        qualifiedOwnerPath: String,
        argumentTypes: List<TypeInfo>
    ): ClassMember.Constructor? =
        classMemberTable[qualifiedOwnerPath]
            ?.get(ClassMember.MemberType.CONSTRUCTOR)
            ?.mapAs<ClassMember, ClassMember.Constructor>()
            ?.filter { it.parameterTypeInfos.size == argumentTypes.size }
            ?.find {
                argumentTypes.zip(it.parameterTypeInfos).all { (from, to) ->
                    from.canImplicitCast(to)
                }
            }

    fun findType(qualifiedPath: String): TypeInfo.Class? =
        typeTable[qualifiedPath]?.`as`()

    fun findType(type: Type): TypeInfo? {
        val typeName = type.standardizeType()

        PrimitiveType.findPrimitiveType(typeName)?.let {
            return TypeInfo.Primitive(it)
        }

        if (typeName == "str") {
            return TypeInfo.fromClass(String::class.java)
        }

        return when (val typeInfo = asTypeInfo(type)) {
            is TypeInfo.Array -> {
                when (typeInfo.baseType) {
                    is TypeInfo.Primitive, is TypeInfo.Class, is TypeInfo.GenericConstraint -> typeInfo
                    is TypeInfo.Array -> null // Unreachable
                }
            }

            is TypeInfo.Class -> {
                typeTable[typeName] = typeInfo

                typeInfo
            }

            else -> typeTable[typeName]
        }
    }

    fun findImplementedEqualMethod(classTypeInfo: TypeInfo.Class): ClassMember.Fn {
        var currentClassTypeInfo: TypeInfo.Class? = classTypeInfo

        while (currentClassTypeInfo != null) {
            classMemberTable[currentClassTypeInfo.standardTypePath]
                ?.get(ClassMember.MemberType.FUNCTION)
                ?.find { it.name == "equals" }
                ?.let { return it.`as`() }

            currentClassTypeInfo = currentClassTypeInfo.superClassType
        }

        return ClassMember.Fn.fromMethod(Any::class.java.getDeclaredMethod("equals", Any::class.java))
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
            } else {
                null
            }
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
                    val typeInfo = TypeInfo.fromClass(clazz) as TypeInfo.Class
                    typeTable[typeName] = typeInfo

                    if (!classMemberTable.containsKey(typeName)) {
                        classMemberTable[typeName] = EnumMap(ClassMember.MemberType::class.java)
                        classMemberTable[typeName]!![ClassMember.MemberType.FIELD] = mutableListOf()
                        classMemberTable[typeName]!![ClassMember.MemberType.FUNCTION] = mutableListOf()

                        for (field in clazz.declaredFields) {
                            classMemberTable[typeName]!![ClassMember.MemberType.FIELD]!! += ClassMember.Field.fromField(
                                field
                            )
                        }

                        for (method in clazz.declaredMethods) {
                            classMemberTable[typeName]!![ClassMember.MemberType.FUNCTION]!! += ClassMember.Fn.fromMethod(
                                method
                            )
                        }
                    }

                    return@run typeInfo
                } catch (_: Exception) {
                } catch (_: Error) {
                }

                null
            }
        }
    }

    private fun collectBfsInheritanceTree(entryClass: TypeInfo.Class): Set<TypeInfo.Class> {
        var index = 0
        var previousSize = 0
        val result = mutableListOf(entryClass)

        while (true) {
            val currentSize = result.size

            for (i in index until currentSize) {
                val parentClass = result[i].superClassType

                if (parentClass != TypeInfo.Class.OBJECT_TYPE_INFO) {
                    result.add(parentClass!!)
                }

                val interfaceClasses = result[i].interfaceTypes

                result += interfaceClasses
            }

            if (previousSize == result.size) {
                // No append in current iteration
                break
            } else {
                index = previousSize - 1
                previousSize = result.size
            }
        }

        result += TypeInfo.Class.OBJECT_TYPE_INFO

        return result.toSet()
    }

    private fun String.appendPath(path: String): String =
        if (this.isEmpty()) {
            path
        } else {
            "$this::$path"
        }
}
