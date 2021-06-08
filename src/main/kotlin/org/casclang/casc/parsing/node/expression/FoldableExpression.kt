package org.casclang.casc.parsing.node.expression

import org.casclang.casc.parsing.type.Type

abstract class FoldableExpression<T>(override val type: Type) : Expression<T> where T: FoldableExpression<T>