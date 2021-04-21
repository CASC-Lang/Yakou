package io.github.chaosunity.casc.parse

import io.github.chaosunity.antlr.CASCBaseListener
import io.github.chaosunity.antlr.CASCParser
import io.github.chaosunity.casc.bytecodegen.instructions.Instruction
import io.github.chaosunity.casc.bytecodegen.instructions.PrintVariable
import io.github.chaosunity.casc.bytecodegen.instructions.VariableDeclaration
import io.github.chaosunity.casc.parse.symbol.Variable


class CASCTreeWalkListener : CASCBaseListener() {
    val instructionsQueue = ArrayDeque<Instruction>()
    val variables = mutableMapOf<String, Variable>()

    override fun exitVariable(ctx: CASCParser.VariableContext?) {
        val varName = ctx!!.ID()
        val varValue = ctx.value()
        val varType = varValue.getStart().getType()
        val varIndex = variables.size
        val varTextValue: String = varValue.getText()
        val variable = Variable(varIndex, varType, varTextValue)
        variables[varName.text] = variable
        instructionsQueue += VariableDeclaration(variable)
    }

    override fun exitPrint(ctx: CASCParser.PrintContext?) {
        val varName = ctx!!.ID()

        if (!variables.containsKey(varName.text)) {
            println("ERROR: Variable not declared.")
            return
        }

        val variable = variables[varName.text]
        instructionsQueue += PrintVariable(variable!!)
    }
}