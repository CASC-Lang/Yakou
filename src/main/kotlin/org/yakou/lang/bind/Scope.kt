package org.yakou.lang.bind

class Scope(private val table: Table) {
    val variables: VariableList = VariableList()

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