package io.github.chaosunity.casc.visitor.expression.function

import io.github.chaosunity.casc.CASCBaseVisitor
import io.github.chaosunity.casc.CASCParser
import io.github.chaosunity.casc.parsing.node.expression.*
import io.github.chaosunity.casc.parsing.scope.Scope
import io.github.chaosunity.casc.visitor.expression.ExpressionVisitor

class CallVisitor(private val ev: ExpressionVisitor, private val scope: Scope) : CASCBaseVisitor<Call<*>>() {
    override fun visitFunctionCall(ctx: CASCParser.FunctionCallContext): Call<*> {
        val functionName = ctx.findFunctionName()!!.text

        if (functionName == scope.className) {
            throw RuntimeException("Function '$functionName's name conflicts to parent class name.")
        }

        val arguments = collectArguments(ctx.findArgument())
        val signature = scope.getMethodCallSignature(functionName, arguments)
        val owner = ctx.owner

        if (owner != null) {
            val owner = owner.accept(ev)

            return FunctionCall(signature, arguments, owner)
        }

        return FunctionCall(signature, arguments, VariableReference(scope.classType, "this"))
    }

    override fun visitConstructorCall(ctx: CASCParser.ConstructorCallContext): Call<*> {
        val className = scope.className
        val arguments = collectArguments(ctx.findArgument())

        return ConstructorCall(className, arguments)
    }

    override fun visitSuperCall(ctx: CASCParser.SuperCallContext): Call<*> {
        val arguments = collectArguments(ctx.findArgument())

        return SuperCall(arguments)
    }

    private fun collectArguments(list: List<CASCParser.ArgumentContext>): List<Argument> {
        val aev = ArgumentVisitor(ev)

        return list.map { it.accept(aev) }
    }
}