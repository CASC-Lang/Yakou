package io.github.chaosunity.casc.visitor

import io.github.chaosunity.antlr.CASCBaseVisitor
import io.github.chaosunity.casc.exception.VisitorReturnNoValueException
import org.antlr.v4.runtime.ParserRuleContext

class CompositeVisitor<T>(private vararg val visitors: CASCBaseVisitor<out T>) {
    fun accept(ctx: ParserRuleContext): T? =
        visitors.map(ctx::accept).find { it != null } ?: throw VisitorReturnNoValueException()
}