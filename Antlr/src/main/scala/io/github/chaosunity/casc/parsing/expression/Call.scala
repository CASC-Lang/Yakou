package io.github.chaosunity.casc.parsing.expression

import java.util

trait Call extends Expression {
    def arguments: util.List[Expression]

    def identifier: String
}
