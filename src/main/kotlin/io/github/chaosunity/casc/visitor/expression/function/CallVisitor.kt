package io.github.chaosunity.casc.visitor.expression.function

import io.github.chaosunity.casc.CASCBaseVisitor
import io.github.chaosunity.casc.CASCParser
import io.github.chaosunity.casc.parsing.node.expression.*
import io.github.chaosunity.casc.parsing.scope.CallingScope
import io.github.chaosunity.casc.parsing.scope.LocalVariable
import io.github.chaosunity.casc.parsing.scope.Scope
import io.github.chaosunity.casc.parsing.type.ClassType
import io.github.chaosunity.casc.visitor.expression.ExpressionVisitor
import kotlin.Exception

class CallVisitor(private val ev: ExpressionVisitor, private val scope: Scope) : CASCBaseVisitor<Call<*>>() {
    override fun visitFieldCall(ctx: CASCParser.FieldCallContext): Call<*> {
        val fieldName = ctx.ID()!!.text
        val ownerCtx = ctx.owner

        if (ownerCtx != null) {
            val owner = ownerCtx.accept(ev)
            val field = scope.getField(owner.type, fieldName)

            return FieldCall(owner, fieldName, field.type, field.static)
        }

        if (scope.callingScope == CallingScope.STATIC)
            scope.fields.clear()

        val field = scope.getField(fieldName)
        val thisVariable = LocalVariable("self", scope.classType)

        return FieldCall(LocalVariableReference(thisVariable), fieldName, field.type, field.static)
    }

    override fun visitFunctionCall(ctx: CASCParser.FunctionCallContext): Call<*> {
        val functionName = ctx.findFunctionName()!!.text
        val arguments = collectArguments(ctx.findArgument())
        val ownerCtx = ctx.owner

        try {
            if (ownerCtx == null) {
                val classType = ClassType(functionName)
                if (functionName != scope.className) {
                    val type = classType.classType()
                }

                return ConstructorCall(functionName, arguments)
            }
        } catch (e: Exception) {
            // then it's not a function but constructor.
        }

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