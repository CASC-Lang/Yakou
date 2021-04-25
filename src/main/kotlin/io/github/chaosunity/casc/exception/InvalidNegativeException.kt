package io.github.chaosunity.casc.exception

import io.github.chaosunity.casc.parsing.type.Type

class InvalidNegativeException(type: Type) :
    RuntimeException("Type '${type.name()}' cannot be applied with negative operator.")