package org.yakou.lang.compilation

import chaos.unity.nenggao.CharacterSet
import chaos.unity.nenggao.FileReportBuilder
import chaos.unity.nenggao.SourceCache
import org.yakou.lang.api.AbstractPreference
import org.yakou.lang.ast.Token
import org.yakou.lang.ast.YkFile
import org.yakou.lang.lexer.Lexer
import org.yakou.lang.parser.Parser
import java.io.File

class CompilationUnit(val sourceFile: File, val preference: AbstractPreference) {
    val maxLineCount = SourceCache.INSTANCE.getOrAdd(sourceFile).lines.size
    val reportBuilder: FileReportBuilder = FileReportBuilder.sourceFile(sourceFile)
        .enableColor(preference.enableColor)
        .characterSet(if (preference.useAscii) CharacterSet.ASCII else CharacterSet.UNICODE)
        .relativePath(preference.sourceFile!!.toPath())
    var tokens: List<Token>? = null
    var ykFile: YkFile? = null

    fun lex(): Boolean {
        tokens = Lexer(this).lex()
        return dumpReportStatus()
    }

    fun parse(): Boolean {
        ykFile = Parser(this).parse()
        return dumpReportStatus()
    }

    private fun dumpReportStatus(): Boolean {
        val hasError = reportBuilder.containsError()
        reportBuilder.dump(preference.outputStream)
        return !hasError
    }
}