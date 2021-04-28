package io.github.chaosunity.casc.exceptions

import io.github.chaosunity.casc.parsing.`type`.Type
import io.github.chaosunity.casc.parsing.scope.Scope

import java.util
import scala.jdk.CollectionConverters.ListHasAsScala

class MethodSignatureNotFoundException(scope: Scope, methodName: String, types: util.List[Type])
    extends RuntimeException(s"Method '$methodName' with the following types : ${types.asScala.map { t => s"${t.internalName}" }.concat(", ")} does not exist in scope '$scope'") {
}
