package io.github.chaosunity.casc.util

import io.github.chaosunity.antlr.CASCParser
import io.github.chaosunity.casc.parsing.`type`.{BuiltInType, ClassType, Type}
import org.apache.commons.lang3.{BooleanUtils, StringUtils}
import org.apache.commons.lang3.math.NumberUtils
import org.apache.commons.validator.routines.{DoubleValidator, FloatValidator, IntegerValidator}

object TypeResolver {
    def getFromTypeName(typeContext: CASCParser.TypeContext): Type = {
        if (typeContext == null) return BuiltInType.VOID

        val typeName = typeContext.getText
        val builtInType = getBuiltInType(typeName)

        if (typeName.equals("java.lang.String")) return BuiltInType.STRING

        if (builtInType.isDefined) return builtInType.get

        new ClassType(typeName)
    }

    def getFromValue(value: String): Type =
        if (StringUtils.isEmpty(value)) BuiltInType.VOID
        else if (NumberUtils.isCreatable(value)) {
            if (IntegerValidator.getInstance().validate(value) != null) BuiltInType.INT
            else if (FloatValidator.getInstance().validate(value) != null) BuiltInType.FLOAT
            else if (DoubleValidator.getInstance().validate(value) != null) BuiltInType.DOUBLE
            else throw new RuntimeException(s"$value is not a valid number syntax.")
        } else if (BooleanUtils.toBoolean(value)) BuiltInType.BOOLEAN
        else BuiltInType.STRING

    def getValueFromString(value: String, t: Type): Any =
        if (TypeChecker.isInteger(t)) value.toInt
        else if (TypeChecker.isFloat(t)) value.toFloat
        else if (TypeChecker.isDouble(t)) value.toDouble
        else if (TypeChecker.isBoolean(t)) value.toBoolean
        else if (t == BuiltInType.STRING) {
            var trimmedString = StringUtils.removeStart(value, "\"")
            trimmedString = StringUtils.removeEnd(trimmedString, "\"")

            trimmedString
        } else throw new RuntimeException("Object is not implemented yet.")

    private def getBuiltInType(typeName: String): Option[Type] =
        BuiltInType.enumSet.find(_.isAlias(typeName))
}
