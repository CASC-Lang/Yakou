package org.casclang.casc.visitor.util

import org.antlr.v4.kotlinruntime.tree.TerminalNode
import org.casclang.casc.CASCBaseVisitor
import org.casclang.casc.CASCParser
import org.casclang.casc.parsing.scope.QualifiedName

object QualifiedNameVisitor : CASCBaseVisitor<QualifiedName>() {
    override fun visitQualifiedName(ctx: CASCParser.QualifiedNameContext): QualifiedName =
        if (ctx.ID().size > 1) {
            val qualifiedPath = ctx.ID()
                .subList(0, ctx.ID().lastIndex)
                .map(TerminalNode::text)
                .reduce { a, b -> "$a.$b" }

            QualifiedName(qualifiedPath, ctx.ID().last().text)
        } else {
            QualifiedName("", ctx.ID(0).text)
        }
}