package io.github.chaosunity.casc.exceptions

import io.github.chaosunity.casc.parsing.`type`.Type

class TypeUnmatchedException(typeA: Type, typeB: Type)
    extends RuntimeException(s"Type ${typeA.name} and ${typeB.name} are not same type."){
}
