package org.yakou.lang.bind

import org.yakou.lang.ast.Token

class Scope(private val table: Table) {
    val variables: VariableList = VariableList()

    fun currentVariableIndex(): Int =
        variables.currentIndex

    // does not check duplication since it was checked at declaration checking phase
    fun addValueParameter(nameToken: Token, typeInfo: TypeInfo) {
        val valueParameter = Variable.ValueParameter(nameToken)

        valueParameter.typeInfo = typeInfo

        variables.add(valueParameter)
    }

    fun addVariable(nameToken: Token, typeInfo: TypeInfo): Boolean {
        val variable = Variable(nameToken)

        variable.typeInfo = typeInfo

        return variables.add(variable)
    }

    fun getVariable(name: String): Variable? =
        variables.find { it.name == name }

    class VariableList : HashSet<Variable>() {
        var currentIndex: Int = 0

        override fun add(element: Variable): Boolean {
            val result = super.add(element)

            if (result) {
                currentIndex += element.size()
            }

            return result
        }
    }
}