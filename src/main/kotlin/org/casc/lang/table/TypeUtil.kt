package org.casc.lang.table

import org.casc.lang.compilation.Preference
import org.casc.lang.parser.Parser

object TypeUtil {
    /**
     * asType takes qualified type name and globe scope to lookup types
     */
    fun asType(name: String): Type? =
        asArrayType(name)
            ?: getLoadedType(name)
            ?: PrimitiveType.values.find { it.typeName == name }
    //      ?: TODO: Cache un-compiled local classes so all the types are properly handled

    fun asType(reference: Reference?): Type? =
        if (reference == null) null
        else asType(reference.toString())

    private fun asArrayType(name: String): ArrayType? =
        if (name.substring(name.length - 2 until name.length) == "[]") {
            val baseType = asType(name.substring(0 until name.length - 2))

            if (baseType == null) null
            else ArrayType(baseType)
        } else null

    private fun getLoadedType(name: String): Type? = try {
        val clazz = Preference.classLoader?.loadClass(name)

        if (clazz == null) null
        else ClassType(clazz)
    } catch (e: Exception) {
        null
    }

    fun checkType(name: String): Boolean =
        asType(name) != null

    fun checkType(reference: Reference?): Boolean =
        asType(reference) != null
}