package org.yakou.lang.compilation

enum class UnitProcessResult(val stateLiteral: String) {
    PASSED("passed"),
    FAILED("failed"),
    SKIPPED("skipped");
    
    fun ok(): Boolean = this != FAILED
}