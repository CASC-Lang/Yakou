package io.github.chaosunity.casc.visitor.util

import io.github.chaosunity.casc.CASCBaseVisitor
import io.github.chaosunity.casc.CASCParser

class QualifiedNameVisitor : CASCBaseVisitor<String>() {
    override fun visitQualifiedName(ctx: CASCParser.QualifiedNameContext): String {
        return ctx.text.replace("::", ".")
    }
}