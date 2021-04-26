package io.github.chaosunity.casc.util

import io.github.chaosunity.casc.parsing.`type`.{BuiltInType, Type}

object TypeChecker {
    def isInteger(`type`: Type): Boolean =
        `type` == BuiltInType.INT

    def isBoolean(`type`: Type): Boolean =
        `type` == BuiltInType.BOOLEAN

    def isFloat(`type`: Type): Boolean =
        `type` == BuiltInType.FLOAT

    def isDouble(`type`: Type): Boolean =
        `type` == BuiltInType.INT
}
