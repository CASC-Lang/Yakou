package org.yakou.lang.checker

import org.yakou.lang.ast.YkFile
import org.yakou.lang.compilation.CompilationUnit

class Checker(private val compilationUnit: CompilationUnit) {
    fun check() {
        checkYkFile(compilationUnit.ykFile!!)
    }

    fun checkYkFile(ykFile: YkFile) {
        // TODO: Implement
    }
}