package io.github.chaosunity.casc.parsing.scope

import com.google.common.collect.Lists
import io.github.chaosunity.casc.exceptions.{LocalVariableNotFoundException, MethodSignatureNotFoundException}
import io.github.chaosunity.casc.parsing.`type`.BuiltInType
import io.github.chaosunity.casc.parsing.global.Metadata
import org.apache.commons.lang3.reflect.MethodUtils
import org.apache.commons.lang3.{ClassUtils, RegExUtils, StringUtils}

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

    def getMethodCallSignature(identifier: String): FunctionSignature =
        if (identifier.equals("super")) new FunctionSignature(identifier, Lists.newArrayList(), BuiltInType.VOID)
        else _functionSignatures.find(_.name.equals(identifier)).getOrElse(throw new MethodSignatureNotFoundException(this, identifier))

    def getSignatureOnClassPath(fullMethodName: String): FunctionSignature = {
        val methodName = RegExUtils.removePattern(fullMethodName, ".*\\.")
        val className = fullMethodName

        var methodOwnerClass: Class[_] = null

        try {
            methodOwnerClass = ClassUtils.getClass(className)
        }catch {
            case e: ClassNotFoundException => throw e
        }

        val accessibleMethod = MethodUtils.getAccessibleMethod(methodOwnerClass, methodName)

        if (accessibleMethod != null) {
            val signature = ReflectionMappingFactory.
        }
    }


    def addLocalVariable(localVariable: LocalVariable): Unit =
        _localVariables += localVariable

    def getLocalVariable(variableName: String): LocalVariable =
        _localVariables.find(_.name.equals(variableName))
            .getOrElse(throw new LocalVariableNotFoundException(this, variableName))

    def localVariableExists(variableName: String): Boolean =
        _localVariables.forall(_.name.equals(variableName))

    def getLocalVariableIndex(variableName: String): Int =
        _localVariables.indexOf(getLocalVariable(variableName))

    def className: String = _metadata.className

    private def getSuperClassName: String = _metadata.superClassName
}
