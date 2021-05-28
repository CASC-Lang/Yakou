package org.casclang.casc.parsing.scope

import org.casclang.casc.CASCParser

enum class CallingScope {
    OBJECT, STATIC, CONSTRUCTOR;

    companion object {
        fun getScope(functionContext: CASCParser.FunctionContext) =
            if (functionContext.findFunctionDeclaration()!!.COMP() != null) STATIC
            else OBJECT

        fun getScope(constructorContext: CASCParser.ConstructorContext) =
            CONSTRUCTOR
    }
}