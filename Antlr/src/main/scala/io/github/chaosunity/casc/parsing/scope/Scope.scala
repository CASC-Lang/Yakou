package io.github.chaosunity.casc.parsing.scope

import io.github.chaosunity.casc.exceptions.{LocalVariableNotFoundException, MethodSignatureNotFoundException}
import io.github.chaosunity.casc.parsing.global.Metadata

import scala.collection.mutable.ListBuffer

class Scope(private val _metadata: Metadata) {
    private var _functionSignatures: ListBuffer[FunctionSignature] = ListBuffer()
    private var _localVariables: ListBuffer[LocalVariable] = ListBuffer()

    def this(scope: Scope) = {
        this(scope._metadata)
        _functionSignatures = scope._functionSignatures.clone()
        _localVariables = scope._localVariables.clone()
    }

    def addSignature(signature: FunctionSignature): Unit =
        _functionSignatures += signature

    def getSignature(methodName: String): FunctionSignature =
        _functionSignatures.find(_.name.equals(methodName))
            .getOrElse(throw new MethodSignatureNotFoundException(this, methodName))

    def addLocalVariable(localVariable: LocalVariable): Unit =
        _localVariables += localVariable

    def getLocalVariable(variableName: String): LocalVariable =
        _localVariables.find(_.name.equals(variableName))
            .getOrElse(throw new LocalVariableNotFoundException(this, variableName))

    def getLocalVariableIndex(variableName: String): Int =
        _localVariables.indexOf(getLocalVariable(variableName))

    def className: String = _metadata.className
}
