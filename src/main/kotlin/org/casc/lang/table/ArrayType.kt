package org.casc.lang.table

import org.casc.lang.compilation.Preference

data class ArrayType(val baseType: Type,
                     override val typeName: String = "${baseType.typeName}[]",
                     override val descriptor: String = "[${baseType.descriptor}",
                     override val internalName: String = "${baseType.internalName}[]"): Type {
    override fun type(): Class<*>? =
        Preference.classLoader?.loadClass(internalName)
}
