package org.yakou.lang.ast

sealed class Path {
    data class SimplePath(val pathSegments: List<Token>) : Path()
}
