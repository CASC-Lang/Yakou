package io.github.chaosunity.casc.visitor.expression.function

import io.github.chaosunity.casc.CASCBaseVisitor
import io.github.chaosunity.casc.CASCParser
import io.github.chaosunity.casc.parsing.node.expression.*
import io.github.chaosunity.casc.parsing.scope.CallingScope
import io.github.chaosunity.casc.parsing.scope.LocalVariable
import io.github.chaosunity.casc.parsing.scope.Scope
import io.github.chaosunity.casc.parsing.type.ClassType
import io.github.chaosunity.casc.visitor.expression.ExpressionVisitor
import io.github.chaosunity.casc.visitor.util.QualifiedNameVisitor
import kotlin.Exception

class CallVisitor(private val ev: ExpressionVisitor, private val scope: Scope) : CASCBaseVisitor<Call<*>>() {
    private val qnv = QualifiedNameVisitor()

    override fun visitFieldCall(ctx: CASCParser.FieldCallContext): Call<*> {
        val fieldName = ctx.ID()!!.text
        val qualifiedName = ctx.findQualifiedName()
        val ownerCtx = ctx.owner

        if (ownerCtx == null && qualifiedName != null) {
            val className = qualifiedName.accept(qnv)
            val classPathRef = ClassPathReference(className)
            val field = scope.getField(classPathRef.type, fieldName)

            if (!field.static) throw RuntimeException("Field '$fieldName' in class '$className' is non-static field.")

            return FieldCall(classPathRef, fieldName, field.type, true)
        }

        if (ownerCtx != null) {
            val owner = ownerCtx.accept(ev)
            val field = scope.getField(owner.type, fieldName)

            return FieldCall(owner, fieldName, field.type, field.static)
        }

        val field = if (scope.callingScope == CallingScope.STATIC) {
            val innerScope = Scope(scope)
            innerScope.getField(fieldName)
        } else scope.getField(fieldName)
        val thisVariable = LocalVariable("self", scope.classType)

        return FieldCall(LocalVariableReference(thisVariable), fieldName, field.type, field.static)
    }

    override fun visitFunctionCall(ctx: CASCParser.FunctionCallContext): Call<*> {
        val functionName = ctx.findFunctionName()!!.text
        val arguments = collectArguments(ctx.findArgument())
        val qualifiedNameCtx = ctx.findQualifiedName()
        val ownerCtx = ctx.owner

        try {
            if (ownerCtx == null) {
                val classType = if (qualifiedNameCtx != null) {
                    ClassType("${qualifiedNameCtx.accept(qnv)}.$functionName")
                } else ClassType(functionName)

                if (functionName != scope.className) {
                    val type = classType.classType()
                }

                val className =
                    if (qualifiedNameCtx != null) "${qualifiedNameCtx.accept(qnv)}.$functionName" else functionName

                return ConstructorCall(className, arguments)
            }
        } catch (e: Exception) {
            // then it's not a function but constructor.
        }

        if (ownerCtx == null && qualifiedNameCtx != null) {
            val className = qualifiedNameCtx.accept(qnv)
            val classPathRef = ClassPathReference(className)
            val signature = scope.getMethodCallSignature(classPathRef.type, functionName, arguments)

            if (!signature.static) throw RuntimeException(
                "Function '${signature.name}(${
                    signature.parameters.joinToString(
                        ", "
                    )
                })' in class '$className' is non-static"
            )

            return FunctionCall(signature, arguments, classPathRef, signature.static)
        }

        if (ownerCtx != null) {
            val owner = ownerCtx.accept(ev)
            val signature = scope.getMethodCallSignature(owner.type, functionName, arguments)

            return FunctionCall(signature, arguments, owner, signature.static)
        }

        val signature = scope.getMethodCallSignature(functionName, arguments)
        val thisVariable = LocalVariable("self", scope.classType)

        return FunctionCall(signature, arguments, LocalVariableReference(thisVariable), signature.static)
    }

    override fun visitConstructorCall(ctx: CASCParser.ConstructorCallContext): Call<*> {
        val className = ctx.findClassName()!!.accept(qnv)
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