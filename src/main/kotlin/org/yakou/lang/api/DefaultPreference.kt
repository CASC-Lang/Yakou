package org.yakou.lang.api

import chaos.unity.nenggao.CharacterSet
import chaos.unity.nenggao.FileReportBuilder
import java.io.File

class DefaultPreference : AbstractPreference() {
    override fun reportBuilder(currentFile: File): FileReportBuilder =
        FileReportBuilder.sourceFile(currentFile)
            .characterSet(if (useAscii) CharacterSet.ASCII else CharacterSet.UNICODE)
            .enableColor(enableColor)
}