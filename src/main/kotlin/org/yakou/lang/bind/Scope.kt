package org.yakou.lang.bind

import org.yakou.lang.ast.Token

class Scope(internal val table: Table) {
    val typeVariables: MutableList<TypeInfo.GenericConstraint> = mutableListOf()
    val variables: VariableList = VariableList()

    fun addTypeVariable(typeVariable: TypeInfo.GenericConstraint) {
        typeVariables.add(typeVariable)
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
            if (contains(element))
                return false

            val result = super.add(element)

            if (result) {
                currentIndex += element.size()
            }

            return result
        }
    }
}