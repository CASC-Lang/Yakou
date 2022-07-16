package org.yakou.lang.bind

import org.yakou.lang.ast.Token

class Scope(private val table: Table) {
    val variables: VariableList = VariableList()

    fun currentVariableIndex(): Int =
        variables.currentIndex

    // does not check duplication since it was checked at declaration checking phase
    fun addValueParameter(nameToken: Token, typeInfo: TypeInfo) {
        val valueParameter = Variable.ValueParameter(nameToken, currentVariableIndex())

        valueParameter.typeInfo = typeInfo

        variables.add(valueParameter)
    }

    fun addVariable(nameToken: Token, typeInfo: TypeInfo): Variable? {
        val variable = Variable(nameToken, currentVariableIndex())

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