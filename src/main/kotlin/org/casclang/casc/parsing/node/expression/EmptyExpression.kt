package org.casclang.casc.parsing.node.expression

import org.casclang.casc.parsing.type.Type

data class EmptyExpression(override val type: Type) : Expression<EmptyExpression>