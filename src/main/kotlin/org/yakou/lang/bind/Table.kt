package org.yakou.lang.bind

import org.yakou.lang.ast.TokenType
import org.yakou.lang.ast.Type

/**
 * Table class stores type infos in Yakou standard type format.
 * Example standard type formats:
 * - pkgA::pkgB::class   (Normal type or just package path)
 * - [pkgA::pkgB::class] (Array type)
 */
class Table {
    companion object {
        fun standardizeType(type: Type): String = when (type) {
            is Type.Array -> {
                val typeBuilder = StringBuilder()
                var dimensionCount = 1
                var innerType = type.type

                while (innerType is Type.Array) {
                    dimensionCount++
                    innerType = innerType.type
                }

                typeBuilder.append("[".repeat(dimensionCount))
                typeBuilder.append(standardizeType(type))
                typeBuilder.append("]".repeat(dimensionCount))

                typeBuilder.toString()
            }
            is Type.TypePath -> {
                val typeBuilder = StringBuilder()

                for (token in type.path.pathSegments) {
                    if (token.type is TokenType.Identifier) {
                        typeBuilder.append(token.literal)
                    } else if (token.type is TokenType.DoubleColon) {
                        typeBuilder.append("::")
                    }
                }

                typeBuilder.toString()
            }
        }

        private fun asTypeInfo(type: Type): TypeInfo = when (type) {
            is Type.Array -> {
                var finalArrayType: TypeInfo.Array
                var dimensionCount = 0
                var innerType = type.type

                while (innerType is Type.Array) {
                    dimensionCount++
                    innerType = innerType.type
                }

                finalArrayType = TypeInfo.Array(asTypeInfo(innerType))

                while (dimensionCount > 0) {
                    finalArrayType = TypeInfo.Array(finalArrayType)
                    dimensionCount--
                }

                finalArrayType
            }
            is Type.TypePath -> {
                val typeName = standardizeType(type)

                PrimitiveType.findPrimitiveType(typeName)?.let {
                    TypeInfo.Primitive(it)
                } ?: TypeInfo.Type(typeName)
            }
        }
    }

    private val typeTable: HashMap<String, TypeInfo> = hashMapOf()

    fun addType(type: Type): Boolean {
        val typeName = standardizeType(type)

        return if (typeTable.containsKey(typeName)) false
        else {
            typeTable[typeName] = asTypeInfo(type)

            true
        }
    }

    fun findType(type: Type): TypeInfo? {
        val typeName = standardizeType(type)

        PrimitiveType.findPrimitiveType(typeName)?.let {
            return TypeInfo.Primitive(it)
        }

        if (typeName.startsWith("[")) {
            // Array type
            val arrayTypeInfo = asTypeInfo(type)
            val baseType = (arrayTypeInfo as TypeInfo.Array).baseType

            // Check if base type is type path, and validate if type path exists
            return when (baseType) {
                is TypeInfo.Primitive -> arrayTypeInfo
                is TypeInfo.Type -> if (typeTable.containsKey(typeName)) arrayTypeInfo else null
                is TypeInfo.Array -> null // Unreachable
            }
        }

        return typeTable[typeName]
    }
}