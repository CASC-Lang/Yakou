package io.github.chaosunity.casc.util

import io.github.chaosunity.casc.parsing.`type`.ClassType
import io.github.chaosunity.casc.parsing.expression.FunctionParameter
import io.github.chaosunity.casc.parsing.scope.FunctionSignature
import scala.collection.JavaConverters._

import java.lang.reflect.{Constructor, Executable, Method, Parameter}
import java.util

object ReflectionMapper {
    def fromMethod(method: Method): FunctionSignature = {
        val (name, parameters) = fromExecutable(method)
        val returnType = method.getReturnType

        new FunctionSignature(name, parameters, new ClassType(returnType.getCanonicalName))
    }

    def fromConstructor(constructor: Constructor[_]): FunctionSignature = {
        val (name, parameters) = fromExecutable(constructor)
        val returnType = constructor.getDeclaringClass

        new FunctionSignature(name, parameters, new ClassType(returnType.getCanonicalName))
    }

    private def fromExecutable(executable: Executable): (String, util.List[FunctionParameter]) =
        (executable.getName, asJava(executable.getParameters.map((param: Parameter) =>
            new FunctionParameter(new ClassType(param.getType.getCanonicalName), param.getName, Option.empty, false)
        )))
}
