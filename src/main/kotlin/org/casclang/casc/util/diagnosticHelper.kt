package org.casclang.casc.util

import org.antlr.v4.kotlinruntime.ParserRuleContext
import org.casclang.casc.compilation.DiagnosticHandler
import org.casclang.casc.compilation.TextSpan

fun addError(filePath: String, startLine: Int, startPos: Int, length: Int, message: String) =
    DiagnosticHandler.addError(TextSpan(filePath, startLine, startPos, length), message)

fun addError(span: TextSpan, message: String) =
    DiagnosticHandler.addError(span, message)

fun addError(ctx: ParserRuleContext?, message: String) =
    DiagnosticHandler.addError(fromContext(ctx), message)