package org.casclang.casc.compilation

internal object DiagnosticHandler {
    private val diagnostics: MutableMap<TextSpan, String> = mutableMapOf()

    fun success() =
        diagnostics.isEmpty()

    fun addError(filePath: String, startLine: Int, startPos: Int, endPos: Int, message: String) =
        diagnostics.put(TextSpan(filePath, startLine, startPos, endPos), message)

    fun addError(span: TextSpan, message: String) =
        diagnostics.put(span, message)

    fun printErrors() =
        diagnostics.forEach {
            it.key.print(it.value)
        }
}