package org.casclang.casc.parsing.node.expression

import org.casclang.casc.parsing.type.ClassType
import org.casclang.casc.parsing.type.Type

data class ClassPathReference(val classpath: String) : Expression<ClassPathReference> {
    override val type: Type = ClassType(classpath)
}