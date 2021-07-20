package org.casclang.casc.compilation

internal object DiagnosticHandler {
    private val diagnostics: MutableList<Pair<TextSpan, String>> = mutableListOf()

    fun success() =
        diagnostics.isEmpty()

    fun addError(filePath: String, startLine: Int, startPos: Int, endPos: Int, message: String) =
        diagnostics.add(TextSpan(filePath, startLine, startPos, endPos) to message)

    fun addError(span: TextSpan, message: String) =
        diagnostics.add(span to message)

    fun printErrors() =
        diagnostics.forEach {
            it.first.print(it.second)
        }
}