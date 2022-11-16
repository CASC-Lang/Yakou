package org.yakou.lang.bind

import org.yakou.lang.ast.Token

class Scope(internal val table: Table, internal val ownerClass: TypeInfo.Class? = null, val isStaticScope: Boolean = true) {
    val typeVariables: MutableMap<String, TypeInfo.GenericConstraint> = mutableMapOf()
    val variables: VariableList = VariableList()

    constructor(scope: Scope, ownerClass: TypeInfo.Class? = scope.ownerClass, isStaticScope: Boolean = true, isStaticInnerScope: Boolean = false) : this(scope.table, ownerClass, isStaticScope) {
        if (!isStaticInnerScope) {
            typeVariables += scope.typeVariables
            variables += scope.variables
        }
    }

    fun addTypeVariable(typeVariable: TypeInfo.GenericConstraint) {
        typeVariables[typeVariable.genericParameterName] = typeVariable
    }

    fun setConstraint(typeParameterName: String, typeInfoVariable: TypeInfo.TypeInfoVariable) {
        typeVariables[typeParameterName]?.bounds?.add(typeInfoVariable)
    }

    private fun currentVariableIndex(): Int =
        variables.currentIndex

    // does not check duplication since it was checked at declaration checking phase
    fun addValueParameter(nameToken: Token, typeInfo: TypeInfo, selfSkipped: Boolean = false) {
        val valueParameter = Variable.ValueParameter(nameToken, currentVariableIndex() + if (selfSkipped) 1 else 0)

        valueParameter.typeInfo = typeInfo

        variables.add(valueParameter)
    }

    fun addVariable(mutToken: Token?, nameToken: Token, typeInfo: TypeInfo): Variable? {
        val variable = Variable(mutToken, nameToken, currentVariableIndex())

        variable.typeInfo = typeInfo

        return if (variables.add(variable)) variable else null
    }

    fun getVariable(name: String): Variable? =
        variables.find { it.name == name }

    fun setPropagatable(name: String) {
        getVariable(name)?.propagatable = true
    }

    class VariableList : ArrayList<Variable>() {
        var currentIndex: Int = 0

        override fun add(element: Variable): Boolean {
            if (contains(element)) {
                return false
            }

            val result = super.add(element)

            if (result) {
                currentIndex += element.size()
            }

            return result
        }
    }
}
