package org.casclang.casc.visitor.expression.function

import org.casclang.casc.CASCBaseVisitor
import org.casclang.casc.CASCParser
import org.casclang.casc.parsing.node.expression.*
import org.casclang.casc.parsing.scope.*
import org.casclang.casc.parsing.type.BuiltInType
import org.casclang.casc.parsing.type.ClassType
import org.casclang.casc.util.addError
import org.casclang.casc.util.fromContext
import org.casclang.casc.visitor.expression.ExpressionVisitor
import org.casclang.casc.visitor.util.QualifiedNameVisitor

class CallVisitor(private val ev: ExpressionVisitor, private val scope: Scope) : CASCBaseVisitor<Call<*>>() {
    override fun visitFieldCall(ctx: CASCParser.FieldCallContext): Call<*> {
        val qualifiedNameCtx = ctx.findQualifiedName()
        val ownerCtx = ctx.owner

        return if (ownerCtx == null) {
            val qualifiedPath = qualifiedNameCtx?.accept(QualifiedNameVisitor)

            if (qualifiedPath != null) {
                val topUsage = if (qualifiedPath.qualifiedName.contains('.'))
                    qualifiedPath.qualifiedName.substring(0 until qualifiedPath.qualifiedName.indexOf('.'))
                else qualifiedPath.reference
                val fieldName = qualifiedPath.reference
                val usage = scope.usages[topUsage]

                fun getField(className: String): FieldCall {
                    ClassType(className).classType()

                    val classPathRef = ClassPathReference(className)
                    val field = scope.getField(classPathRef.type, fieldName)

                    if (field == null)
                        addError(ctx, "Unresolved reference: $fieldName")

                    if (field?.static != true)
                        addError(ctx, "Field ${classPathRef.type.internalName}#$fieldName is not a companion field.")

                    return FieldCall(classPathRef, fieldName, field?.type ?: BuiltInType.VOID, true)
                }

                if (usage != null) {
                    when (usage) {
                        is PathUsage -> {
                            try {
                                val className =
                                    "${usage.qualifiedPath}.${if (usage.qualifiedPath.contains(qualifiedPath.reference)) "" else qualifiedPath.reference}"

                                getField(className.substring(0 until className.lastIndexOf('.')))
                            } catch (e: ClassNotFoundException) {
                                val className = "${usage.qualifiedPath}.${qualifiedPath.removeDuplicate(topUsage)}"

                                getField(className.substring(0 until className.lastIndexOf('.')))
                            }
                        }
                        is ClassUsage -> {
                            getField("${usage.qualifiedPath}.${usage.className}")
                        }
                    }
                } else {
                    getField(qualifiedPath.qualifiedPath)
                }
            } else {
                val fieldName = ctx.ID()!!.text
                val field = scope.getField(fieldName)
                val thisVariable = LocalVariable("self", scope.classType)

                if (field == null)
                    addError(ctx, "Unresolved reference: $fieldName")

                FieldCall(LocalVariableReference(thisVariable), fieldName, field?.type ?: BuiltInType.VOID, field?.static ?: false)
            }
        } else {
            val fieldName = ctx.ID()!!.text
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
        val qualifiedNameCtx = ctx.findQualifiedName()
        val ownerCtx = ctx.owner

        if (ownerCtx == null) {
            val qualifiedPath = qualifiedNameCtx?.accept(QualifiedNameVisitor)

            if (qualifiedPath != null) {
                val topUsage = if (qualifiedPath.qualifiedName.contains('.'))
                    qualifiedPath.qualifiedName.substring(0 until qualifiedPath.qualifiedName.indexOf('.'))
                else qualifiedPath.reference
                val usage = scope.usages[topUsage]

                if (usage != null) {
                    when (usage) {
                        is PathUsage -> {
                            val clazzName =
                                "${usage.qualifiedPath}.${if (usage.qualifiedPath.contains(qualifiedPath.reference)) "" else "${qualifiedPath.reference}."}$functionName"
                            val clazzType = ClassType(clazzName)

                            if (clazzType.isCached())
                                clazzType.tryInitClass()

                            if (clazzType.isClassExists())
                                return ConstructorCall(clazzName, arguments)

                            val className = "${usage.qualifiedPath}.${qualifiedPath.removeDuplicate(topUsage)}"
                            val classType = ClassType(className)
                            val signature = scope.getMethodCallSignature(classType, functionName, arguments)

                            if (signature == null)
                                addError(ctx, "Unresolved reference: $functionName")

                            if (signature != null && !signature.static)
                                addError(ctx, "Function ${classType.internalName}#$functionName() is not a companion function.")

                            return FunctionCall(signature, arguments, classType, true)
                        }
                        is ClassUsage -> {
                            val className = usage.qualifiedName
                            val classType = ClassType(className)

                            if (classType.isCached())
                                classType.tryInitClass()

                            val signature = scope.getMethodCallSignature(classType, functionName, arguments)

                            if (signature == null)
                                addError(ctx, "Unresolved reference: $functionName")

                            if (signature != null && !signature.static)
                                addError(ctx, "Function ${classType.internalName}#$functionName() is not a companion function.")

                            return FunctionCall(signature, arguments, classType, true)
                        }
                    }
                } else {
                    val clazzName = "${qualifiedPath.qualifiedName}.$functionName"
                    val clazzType = ClassType(clazzName)

                    if (clazzType.isCached())
                        clazzType.tryInitClass()

                    if (clazzType.isClassExists())
                        return ConstructorCall(clazzName, arguments)

                    val className = qualifiedPath.qualifiedName
                    val classType = ClassType(className)
                    val signature = scope.getMethodCallSignature(classType, functionName, arguments)

                    if (signature == null)
                        addError(ctx, "Unresolved reference: $functionName")

                    return FunctionCall(signature, arguments, classType, signature?.static ?: false)
                }
            } else {
                val className = (scope.usages[functionName] as ClassUsage?)?.qualifiedName ?: functionName
                val classType = ClassType(className)

                if (classType.isCached())
                    classType.tryInitClass()

                if (classType.isClassExists())
                    return ConstructorCall(className, arguments)

                val signature = scope.getMethodCallSignature(functionName, arguments)
                val thisVariable = LocalVariable("self", scope.classType)

                if (signature == null)
                    addError(ctx, "Unresolved reference: $functionName")

                return FunctionCall(signature, arguments, LocalVariableReference(thisVariable), signature?.static ?: false)

            }
        } else {
            val owner = ownerCtx.accept(ev)
            val signature = scope.getMethodCallSignature(owner.type, functionName, arguments)

            if (signature == null)
                addError(ctx, "Unresolved reference: $functionName")

            return FunctionCall(signature, arguments, owner, signature?.static ?: false)
        }
    }


    override fun visitConstructorCall(ctx: CASCParser.ConstructorCallContext): Call<*> {
        val className = ctx.findClassName()!!.accept(QualifiedNameVisitor)
        val arguments = collectArguments(ctx.findArgument())

        return ConstructorCall(className.qualifiedName, arguments)
    }

    fun buildSelfCall(arguments: List<CASCParser.ArgumentContext>): Call<*> =
        SelfCall(collectArguments(arguments))

    private fun collectArguments(list: List<CASCParser.ArgumentContext>): List<Argument> {
        val aev = ArgumentVisitor(ev)

        return list.map { it.accept(aev) }
    }
}