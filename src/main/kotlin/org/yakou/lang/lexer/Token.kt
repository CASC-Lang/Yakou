package org.yakou.lang.lexer

import org.yakou.lang.Position

data class Token(val literal: String, val type: TokenType, val position: Position)
