package org.yakou.lang.api

import chaos.unity.nenggao.FileReportBuilder
import java.io.File

abstract class AbstractPreference {
    var sourceFile: File? = null
    var outputFolder = File("out")
    var outputStream = System.out
    var enableColor = false
    var useAscii = false
    var noOpt = true
    var enableTiming = false
    abstract fun reportBuilder(currentFile: File): FileReportBuilder?
}
