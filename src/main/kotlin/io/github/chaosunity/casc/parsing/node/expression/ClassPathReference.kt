package io.github.chaosunity.casc.parsing.node.expression

import io.github.chaosunity.casc.parsing.type.ClassType
import io.github.chaosunity.casc.parsing.type.Type

data class ClassPathReference(val classpath: String) : Expression<ClassPathReference> {
    override val type: Type = ClassType(classpath)
}