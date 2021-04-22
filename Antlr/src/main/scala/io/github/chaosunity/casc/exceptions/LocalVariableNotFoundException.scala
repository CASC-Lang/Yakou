package io.github.chaosunity.casc.exceptions

import io.github.chaosunity.casc.parsing.scope.Scope

class LocalVariableNotFoundException(scope: Scope, variableName: String)
    extends RuntimeException(s"Variable '$variableName' does not exist in scope '$scope'") {
}
