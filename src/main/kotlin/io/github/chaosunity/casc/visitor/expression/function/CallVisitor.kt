package io.github.chaosunity.casc.visitor.expression.function

import io.github.chaosunity.casc.CASCBaseVisitor
import io.github.chaosunity.casc.CASCParser
import io.github.chaosunity.casc.parsing.node.expression.*
import io.github.chaosunity.casc.parsing.scope.LocalVariable
import io.github.chaosunity.casc.parsing.scope.Scope
import io.github.chaosunity.casc.visitor.expression.ExpressionVisitor

class CallVisitor(private val ev: ExpressionVisitor, private val scope: Scope) : CASCBaseVisitor<Call<*>>() {
    override fun visitFieldCall(ctx: CASCParser.FieldCallContext): Call<*> {
        val fieldName = ctx.ID()!!.text
        val ownerCtx = ctx.owner

        if (ownerCtx != null) {
            val owner = ownerCtx.accept(ev)
            val field = scope.getField(owner.type, fieldName)

            return FieldCall(owner, fieldName, field.type)
        }

        val field = scope.getField(fieldName)
        val thisVariable = LocalVariable("self", scope.classType)

        return FieldCall(LocalVariableReference(thisVariable), fieldName, field.type)
    }

    override fun visitFunctionCall(ctx: CASCParser.FunctionCallContext): Call<*> {
        val functionName = ctx.findFunctionName()!!.text

        if (functionName == scope.className) {
            throw RuntimeException("Function '$functionName's name conflicts to parent class name.")
        }

        val arguments = collectArguments(ctx.findArgument())
        val ownerCtx = ctx.owner

        if (ownerCtx != null) {
            val owner = ownerCtx.accept(ev)
            val signature = scope.getMethodCallSignature(owner.type, functionName, arguments)

            return FunctionCall(signature, arguments, owner)
        }

        val signature = scope.getMethodCallSignature(functionName, arguments)
        val thisVariable = LocalVariable("self", scope.classType)

        return FunctionCall(signature, arguments, LocalVariableReference(thisVariable))
    }

    override fun visitConstructorCall(ctx: CASCParser.ConstructorCallContext): Call<*> {
        val className = ctx.findClassName()!!.text.replace("::", ".")
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