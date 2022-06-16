package org.yakou.lang.compilation

import chaos.unity.nenggao.CharacterSet
import chaos.unity.nenggao.FileReportBuilder
import org.yakou.lang.api.AbstractPreference
import org.yakou.lang.ast.Token
import org.yakou.lang.lexer.Lexer
import java.io.File

class CompilationUnit(val sourceFile: File, val preference: AbstractPreference) {
    val reportBuilder: FileReportBuilder = FileReportBuilder.sourceFile(sourceFile)
        .enableColor(preference.enableColor)
        .characterSet(if (preference.useAscii) CharacterSet.ASCII else CharacterSet.UNICODE)
        .relativePath(preference.sourceFile!!.toPath())
    var tokens: List<Token>? = null

    fun lex(): Boolean {
        tokens = Lexer(this).lex()
        reportBuilder.print(preference.outputStream)
        return !reportBuilder.containsError()
    }
}