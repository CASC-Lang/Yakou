package org.yakou.lang.bind

import org.yakou.lang.ast.Token

class Scope(private val table: Table) {
    private val variables: VariableList = VariableList()

    fun addVariable(nameToken: Token, typeInfo: TypeInfo) {
        val variable = Variable(nameToken)

        variable.typeInfo = typeInfo

        variables.add(variable)
    }

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