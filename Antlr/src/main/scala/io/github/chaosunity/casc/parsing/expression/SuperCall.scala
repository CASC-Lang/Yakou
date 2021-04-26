package io.github.chaosunity.casc.parsing.expression

import com.google.common.collect.Lists
import io.github.chaosunity.casc.parsing.`type`.{BuiltInType, Type}
import io.github.chaosunity.casc.parsing.expression.SuperCall.SUPER_IDENTIFIER

import java.util

class SuperCall(_arguments: util.List[Expression] = Lists.newArrayList()) extends Call {
    override def arguments: util.List[Expression] = _arguments

    override def identifier: String = SUPER_IDENTIFIER

    override def `type`: Type = BuiltInType.VOID

    override def negative: Boolean = false
}

object SuperCall {
    final val SUPER_IDENTIFIER = "super"
}