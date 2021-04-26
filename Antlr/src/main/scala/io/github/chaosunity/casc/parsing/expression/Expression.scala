package io.github.chaosunity.casc.parsing.expression

import io.github.chaosunity.casc.parsing.`type`.Type
import io.github.chaosunity.casc.parsing.statement.Statement

trait Expression extends Statement {
    def `type`: Type

    def negative: Boolean
}
