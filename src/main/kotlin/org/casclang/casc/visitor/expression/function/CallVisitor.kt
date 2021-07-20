package org.casclang.casc.visitor.expression.function

import org.antlr.v4.kotlinruntime.tree.TerminalNode
import org.casclang.casc.CASCBaseVisitor
import org.casclang.casc.CASCParser
import org.casclang.casc.parsing.node.expression.*
import org.casclang.casc.parsing.scope.Scope
import org.casclang.casc.parsing.type.BuiltInType
import org.casclang.casc.parsing.type.ClassType
import org.casclang.casc.util.addError
import org.casclang.casc.visitor.expression.ExpressionVisitor

class CallVisitor(private val ev: ExpressionVisitor, private val scope: Scope) : CASCBaseVisitor<Call<*>>() {
    override fun visitFieldCall(ctx: CASCParser.FieldCallContext): Call<*> {
        val fieldName = ctx.ID()!!.text
        val referencedClass = ctx.findQualifiedName()
        val ownerCtx = ctx.owner

        return if (ownerCtx == null) {
            val clazzPath = if (referencedClass!!.ID().size > 1) {
                referencedClass.ID()
                    .map(TerminalNode::text)
                    .reduce { a, b -> "$a.$b" }
            } else {
                scope.usages[referencedClass.text]?.fullPath ?: run {
                    addError(referencedClass, "Unresolved reference: ${referencedClass.text}")
                    ""
                }
            }

            val clazzPathRef = ClassPathReference(clazzPath)
            val field = scope.getField(clazzPathRef.type, fieldName)

            if (field?.static != true)
                addError(ctx, "Field $fieldName is not a companion field.")

            FieldCall(clazzPathRef, fieldName, field?.type ?: BuiltInType.VOID, true)
        } else {
            val owner = ownerCtx.accept(ev)
            val field = scope.getField(owner.type, fieldName)

            if (field == null)
                addError(ctx, "Unresolved reference: $fieldName")

            FieldCall(owner, fieldName, field?.type ?: BuiltInType.VOID, field?.static ?: false)
        }
    }

    override fun visitFunctionCall(ctx: CASCParser.FunctionCallContext): Call<*> {
        val functionName = ctx.findFunctionName()!!.text
        val arguments = collectArguments(ctx.findArgument())
        val referencedClass = ctx.findQualifiedName()
        val ownerCtx = ctx.owner

        return if (ownerCtx == null) {
            if (referencedClass == null) {
                val clazzPath = scope.usages[functionName]?.fullPath

                when {
                    clazzPath != null -> {
                        val signature = scope.getConstructorCallSignature(clazzPath, arguments)

                        if (signature == null)
                            addError(ctx, "Unresolved reference: $functionName")

                        ConstructorCall(clazzPath, arguments)
                    }
                    functionName == scope.className -> {
                        val signature = scope.getConstructorCallSignature(functionName, arguments)

                        if (signature == null)
                            addError(ctx, "Unresolved reference: $functionName")

                        ConstructorCall(functionName, arguments)
                    }
                    else -> {
                        val signature = scope.getMethodCallSignature(functionName, arguments)

                        if (signature == null)
                            addError(ctx, "Unresolved reference: $functionName")

                        FunctionCall(signature, arguments, scope.classType, signature?.static ?: false)
                    }
                }
            } else {
                val clazzPath = if (referencedClass.ID().size > 1) {
                    referencedClass.ID()
                        .map(TerminalNode::text)
                        .reduce { a, b -> "$a.$b" }
                } else {
                    scope.usages[referencedClass.text]?.fullPath ?: run {
                        addError(referencedClass, "Unresolved reference: ${referencedClass.text}")
                        ""
                    }
                }
                val clazzType = ClassType(clazzPath)
                val signature = scope.getMethodCallSignature(clazzType, functionName, arguments)

                if (signature == null)
                    addError(ctx, "Unresolved reference: $functionName")

                if (signature != null && !signature.static)
                    addError(
                        ctx,
                        "Function ${clazzType.internalName}#$functionName() is not a companion function."
                    )

                FunctionCall(signature, arguments, clazzType, true)
            }
        } else {
            val owner = ownerCtx.accept(ev)
            val signature = scope.getMethodCallSignature(owner.type, functionName, arguments)

            if (signature == null)
                addError(ctx, "Unresolved reference: $functionName")

            FunctionCall(signature, arguments, owner, signature?.static ?: false)
        }
    }


    override fun visitConstructorCall(ctx: CASCParser.ConstructorCallContext): Call<*> {
        val className = ctx.findClassName()!!.text
        val arguments = collectArguments(ctx.findArgument())

        return ConstructorCall(className, arguments)
    }

    fun buildSelfCall(arguments: List<CASCParser.ArgumentContext>): Call<*> =
        SelfCall(collectArguments(arguments))

    private fun collectArguments(list: List<CASCParser.ArgumentContext>): List<Argument> {
        val aev = ArgumentVisitor(ev)

        return list.map { it.accept(aev) }
    }
}