package org.yakou.lang.compilation

import chaos.unity.nenggao.CharacterSet
import chaos.unity.nenggao.FileReportBuilder
import chaos.unity.nenggao.SourceCache
import java.io.File
import org.yakou.lang.api.AbstractPreference
import org.yakou.lang.ast.Token
import org.yakou.lang.ast.YkFile
import org.yakou.lang.bind.Binder
import org.yakou.lang.checker.Checker
import org.yakou.lang.lexer.Lexer
import org.yakou.lang.optimizer.Optimizer
import org.yakou.lang.parser.Parser

class CompilationUnit(val sourceFile: File, val session: CompilationSession) : UnitReporter {
    val reportBuilder: FileReportBuilder = FileReportBuilder.sourceFile(sourceFile)
        .enableColor(preference().enableColor)
        .characterSet(if (preference().useAscii) CharacterSet.ASCII else CharacterSet.UNICODE)
        .relativePath(preference().sourceFile!!.toPath())
    var tokens: List<Token>? = null
    var ykFile: YkFile? = null
    private lateinit var binder: Binder

    override fun preference(): AbstractPreference =
        session.preference

    override fun reporter(): FileReportBuilder =
        reportBuilder

    override fun maxLineCount(): Int =
        SourceCache.INSTANCE.getOrAdd(sourceFile).size

    fun lex(): UnitProcessResult {
        tokens = Lexer(this).lex()
        return dumpReportStatus()
    }

    fun parse(): UnitProcessResult {
        ykFile = Parser(this).parse()
        return dumpReportStatus()
    }

    fun bind(): UnitProcessResult {
        binder = Binder(this)
        binder.bind()
        return dumpReportStatus()
    }

    fun postBind(): UnitProcessResult {
        binder.bindSecondary()
        return dumpReportStatus()
    }

    fun check(): UnitProcessResult {
        Checker(this).check()
        return dumpReportStatus()
    }

    fun optimize(): UnitProcessResult {
        Optimizer(this).optimize()
        return dumpReportStatus()
    }

    private fun dumpReportStatus(): UnitProcessResult {
        val hasError = reportBuilder.containsError()
        reportBuilder.dump(preference().outputStream)
        return if (!hasError) UnitProcessResult.PASSED else UnitProcessResult.FAILED
    }
}
