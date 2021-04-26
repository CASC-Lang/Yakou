package io.github.chaosunity.casc.parsing.expression

import com.google.common.collect.Lists
import io.github.chaosunity.casc.parsing.`type`.{ClassType, Type}

import java.util

class ConstructorCall(val className: String,
                      arg: util.List[Expression]) extends Call {
    def this(identifier: String) {
        this(identifier, Lists.newArrayList())
    }

    override def arguments: util.List[Expression] = arg

    override def identifier: String = className

    override def `type`: Type = new ClassType(className)

    override def negative: Boolean = false
}
