package org.casclang.casc.parsing.node.expression

import org.casclang.casc.parsing.type.Type

data class Value(override val type: Type, val value: String) : FoldableExpression<Value>(type)
