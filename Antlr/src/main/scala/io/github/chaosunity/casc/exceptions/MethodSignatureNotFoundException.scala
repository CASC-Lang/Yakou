package io.github.chaosunity.casc.exceptions

import io.github.chaosunity.casc.parsing.scope.Scope

class MethodSignatureNotFoundException(scope: Scope, methodName: String)
    extends RuntimeException(s"Method '$methodName' does not exist in scope '$scope'") {
}
