package io.github.chaosunity.casc.parsing.scope

import com.google.common.collect.Lists
import io.github.chaosunity.casc.exceptions.{LocalVariableNotFoundException, MethodSignatureNotFoundException}
import io.github.chaosunity.casc.parsing.`type`.{BuiltInType, ClassType, Type}
import io.github.chaosunity.casc.parsing.expression.Expression
import io.github.chaosunity.casc.parsing.global.Metadata
import io.github.chaosunity.casc.util.ReflectionMapper
import org.apache.commons.lang3.reflect.{ConstructorUtils, MethodUtils}
import org.apache.commons.lang3.{ClassUtils, RegExUtils}

import java.util
import java.util.{Collections, Optional}
import scala.collection.mutable.ListBuffer
import scala.jdk.CollectionConverters.{CollectionHasAsScala, SeqHasAsJava}

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

    def parameterLessSignatureExists(identifier: String): Boolean =
        signatureExists(identifier, Collections.emptyList())

    def signatureExists(identifier: String, parameters: util.List[Type]): Boolean = {
        if (identifier.equals("super")) return true
        _functionSignatures.forall(_.matches(identifier, parameters))
    }

    def getMethodCallSignatureWithoutParameters(identifier: String): FunctionSignature =
        getMethodCallSignature(identifier, new util.ArrayList[Type]())

    def getMethodCallSignature(identifier: String, arguments: util.Collection[Expression]): FunctionSignature =
        getMethodCallSignature(identifier, arguments.asScala.map(_.`type`).toList.asJava)

    def getMethodCallSignature(identifier: String, parameterTypes: util.List[Type]): FunctionSignature =
        if (identifier.equals("super")) new FunctionSignature(identifier, Lists.newArrayList(), BuiltInType.VOID)
        else _functionSignatures.find(_.matches(identifier, parameterTypes)).getOrElse(throw new MethodSignatureNotFoundException(this, identifier, parameterTypes))

    def getSignatureOnClassPath(fullMethodName: String): Optional[FunctionSignature] = {
        val methodName = RegExUtils.removePattern(fullMethodName, ".*\\.")
        val className = fullMethodName

        var methodOwnerClass: Class[_] = null

        try {
            methodOwnerClass = ClassUtils.getClass(className)
        } catch {
            case e: ClassNotFoundException => throw e
        }

        val accessibleMethod = MethodUtils.getAccessibleMethod(methodOwnerClass, methodName)

        if (accessibleMethod != null) {
            val signature = ReflectionMapper.fromMethod(accessibleMethod)

            return Optional.of(signature)
        }

        val accessibleConstructor = ConstructorUtils.getAccessibleConstructor(methodOwnerClass)

        if (accessibleConstructor != null) {
            val signature = ReflectionMapper.fromConstructor(accessibleConstructor)

            return Optional.of(signature)
        }

        Optional.empty()
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

    def classInternalName: String = classType.internalName

    def superClassInternalName: String = new ClassType(superClassName).internalName

    def classType: Type = new ClassType(className)

    private def superClassName: String = _metadata.superClassName
}
