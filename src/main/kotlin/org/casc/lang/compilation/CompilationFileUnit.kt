package org.casc.lang.compilation

import org.casc.lang.ast.File
import org.casc.lang.table.Scope

data class CompilationFileUnit(val fileName: String, val source: List<String>, val relativePath: String) {
    var reports: List<Report> = mutableListOf()
    lateinit var file: File
    lateinit var scope: Scope

    fun printReports() =
        reports.forEach { it.printReport(relativePath, source) }

    fun anyError() =
        reports.filterIsInstance<Error>().isNotEmpty()
}
