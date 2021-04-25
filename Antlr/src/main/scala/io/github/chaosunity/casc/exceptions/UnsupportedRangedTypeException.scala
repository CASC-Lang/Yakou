package io.github.chaosunity.casc.exceptions

import io.github.chaosunity.casc.parsing.`type`.Type

class UnsupportedRangedTypeException(startType: Type, endType: Type)
    extends RuntimeException(s"Unsupported ranged types, start type: ${startType.name}, end type: ${endType.name}") {

}
