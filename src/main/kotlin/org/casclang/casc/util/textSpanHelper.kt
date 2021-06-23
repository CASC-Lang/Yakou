package org.casclang.casc.util

import org.antlr.v4.kotlinruntime.ParserRuleContext
import org.casclang.casc.compilation.Compiler
import org.casclang.casc.compilation.TextSpan

fun fromContext(ctx: ParserRuleContext?): TextSpan {
    val currentFile = Compiler.compilation.currentFile?.absolutePath ?: "Unknown Source"
    val startLine = ctx?.start?.line ?: -1
    val startPos = ctx?.start?.charPositionInLine ?: -1
    val endPos = ctx?.stop?.endPoint()?.column ?: -1

    return TextSpan(currentFile, startLine, startPos, endPos)
}