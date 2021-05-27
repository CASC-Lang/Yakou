package io.github.chaosunity.casc.visitor.expression.function

import io.github.chaosunity.casc.CASCBaseVisitor
import io.github.chaosunity.casc.CASCParser
import io.github.chaosunity.casc.parsing.node.expression.*
import io.github.chaosunity.casc.parsing.scope.*
import io.github.chaosunity.casc.parsing.type.ClassType
import io.github.chaosunity.casc.visitor.expression.ExpressionVisitor
import io.github.chaosunity.casc.visitor.util.QualifiedNameVisitor

class CallVisitor(private val ev: ExpressionVisitor, private val scope: Scope) : CASCBaseVisitor<Call<*>>() {
    override fun visitFieldCall(ctx: CASCParser.FieldCallContext): Call<*> {
        val fieldName = ctx.ID()!!.text
        val qualifiedName = ctx.findQualifiedName()
        val ownerCtx = ctx.owner

        if (ownerCtx == null && qualifiedName != null) {
            val className = qualifiedName.accept(QualifiedNameVisitor)
            val classPathRef = ClassPathReference(className.qualifiedName)
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

        return if (ownerCtx == null) {
            val qualifiedPath = qualifiedNameCtx?.accept(QualifiedNameVisitor)

            if (qualifiedPath != null) {
                val topUsage = if (qualifiedPath.qualifiedName.contains('.'))
                    qualifiedPath.qualifiedName.substring(0 until qualifiedPath.qualifiedName.indexOf('.'))
                else qualifiedPath.reference
                val usage = scope.usages[topUsage]

                if (usage != null) {
                    when (usage) {
                        is PathUsage -> {
                            try {
                                val className =
                                    "${usage.qualifiedPath}.${if (usage.qualifiedPath.contains(qualifiedPath.reference)) "" else "${qualifiedPath.reference}."}$functionName"

                                ClassType(className).classType()

                                ConstructorCall(className, arguments)
                            } catch (e: ClassNotFoundException) {
                                val className = "${usage.qualifiedPath}.${qualifiedPath.removeDuplicate(topUsage)}"

                                val classType = ClassType(className)

                                classType.classType()

                                val signature = scope.getMethodCallSignature(classType, functionName, arguments)

                                if (!signature.static)
                                    throw RuntimeException("Function ${classType.internalName}#$functionName() is not a companion function.")

                                FunctionCall(signature, arguments, classType, true)
                            }
                        }
                        is ClassUsage -> {
                            val className = "${usage.qualifiedPath}.${usage.className}"

                            val classType = ClassType(className)

                            classType.classType()

                            val signature = scope.getMethodCallSignature(classType, functionName, arguments)

                            if (!signature.static)
                                throw RuntimeException("Function ${classType.internalName}#$functionName() is not a companion function.")

                            FunctionCall(signature, arguments, classType, true)
                        }
                    }
                } else {
                    try {
                        val className = "${qualifiedPath.qualifiedName}.$functionName"
                        ClassType(className).classType()

                        ConstructorCall(className, arguments)
                    } catch (e: ClassNotFoundException) {
                        val className = qualifiedPath.qualifiedName
                        val classType = ClassType(className)
                        val signature = scope.getMethodCallSignature(classType, functionName, arguments)

                        FunctionCall(signature, arguments, classType, signature.static)
                    }
                }
            } else {
                try {
                    val className = (scope.usages[functionName] as ClassUsage?)?.qualifiedName ?: kotlin.run {
                        ClassType(functionName).classType()

                        functionName
                    }

                    ConstructorCall(className, arguments)
                } catch (e: ClassNotFoundException) {
                    val signature = scope.getMethodCallSignature(functionName, arguments)
                    val thisVariable = LocalVariable("self", scope.classType)

                    FunctionCall(signature, arguments, LocalVariableReference(thisVariable), signature.static)
                }
            }
        } else {
            val owner = ownerCtx.accept(ev)
            val signature = scope.getMethodCallSignature(owner.type, functionName, arguments)

            FunctionCall(signature, arguments, owner, signature.static)
        }
    }

    override fun visitConstructorCall(ctx: CASCParser.ConstructorCallContext): Call<*> {
        val className = ctx.findClassName()!!.accept(QualifiedNameVisitor)
        val arguments = collectArguments(ctx.findArgument())

        return ConstructorCall(className.qualifiedName, arguments)
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