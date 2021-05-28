package org.casclang.casc.parsing.type

import jdk.internal.org.objectweb.asm.Opcodes

interface Type {
    val typeName: String
    fun classType(): Class<*>?
    val descriptor: String
    val internalName: String

    val loadVariableOpcode: Int
    val storeVariableOpcode: Int
    val returnOpcode: Int
    val addOpcode: Int
    val subtractOpcode: Int
    val multiplyOpcode: Int
    val divideOpcode: Int
    val negativeOpcode: Int

    val typeOpcode: Int
    val arrayLoadOpcode: Int
    val arrayStoreOpcode: Int

    fun isByte(): Boolean =
        this == BuiltInType.BYTE

    fun isShort(): Boolean =
        this == BuiltInType.SHORT

    fun isInt(): Boolean =
        this == BuiltInType.INT

    fun isLong(): Boolean =
        this == BuiltInType.LONG

    fun isFloat(): Boolean =
        this == BuiltInType.FLOAT

    fun isDouble(): Boolean =
        this == BuiltInType.DOUBLE

    fun isNumeric(): Boolean =
        isByte() || isShort() || isInt() || isLong() || isFloat() || isDouble()

    fun isBool(): Boolean =
        this == BuiltInType.BOOLEAN

    fun isString(): Boolean =
        this == BuiltInType.STRING

    fun isBuiltInType() =
        BuiltInType.values().any { it == this }
}