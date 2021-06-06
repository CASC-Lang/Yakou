package org.casclang.casc.visitor

import org.casclang.casc.CASCBaseVisitor
import org.casclang.casc.CASCParser
import org.casclang.casc.parsing.ClassDeclaration
import org.casclang.casc.parsing.Constructor
import org.casclang.casc.parsing.Function
import org.casclang.casc.parsing.MetaData
import org.casclang.casc.parsing.node.expression.FieldReference
import org.casclang.casc.parsing.node.expression.LocalVariableReference
import org.casclang.casc.parsing.node.expression.Parameter
import org.casclang.casc.parsing.node.statement.Assignment
import org.casclang.casc.parsing.node.statement.Block
import org.casclang.casc.parsing.scope.*
import org.casclang.casc.parsing.type.BuiltInType
import org.casclang.casc.util.TypeResolver
import org.casclang.casc.visitor.expression.ExpressionVisitor
import org.casclang.casc.visitor.expression.function.ParameterVisitor

class ClassVisitor(private val usages: List<Usage> = listOf()) : CASCBaseVisitor<ClassDeclaration>() {
    private lateinit var scope: Scope

    override fun visitClassDeclaration(ctx: CASCParser.ClassDeclarationContext): ClassDeclaration {
        val accessModifier = AccessModifier.getModifier(ctx.findOuterAccessMods()?.text)
        val name = ctx.findClassName()!!.text
        val metadata = MetaData(name, "java.lang.Object")

        scope = Scope(metadata, usages)

        val primaryCtorCtx = ctx.findPrimaryConstructor()

        val fieldVisitor = FieldVisitor(scope)
        val fieldDeclarationVisitor = FieldDeclarationVisitor(scope)
        val functionSignatureVisitor = FunctionSignatureVisitor(scope)
        val ctorCtx = ctx.findClassBody()!!.findConstructor()
        val functionsCtx = ctx.findClassBody()!!.findFunction()

        // all class properties
        val functions = mutableListOf<Function<*>>()
        val fields = mutableListOf<Field>()

        if (primaryCtorCtx != null) {
            val ctorAccess = AccessModifier.getModifier(primaryCtorCtx.ctorAccessMod?.text)
            val parameterCtx = primaryCtorCtx.findConstructorParameter()
            val parameterFields = mutableListOf<Field>()
            val parameters = mutableListOf<Parameter>()

            // We first collect all field parameters
            val ev = ExpressionVisitor(scope)

            parameterCtx.onEach {
                if (it.PARAM == null) {
                    parameterFields += Field(
                        it.MUT() == null,
                        scope.classType,
                        it.findParameter()!!.ID()!!.text,
                        TypeResolver.getFromTypeReferenceContext(it.findParameter()!!.findTypeReference()),
                        AccessModifier.getModifier(it.findInnerAccessMods()?.text)
                    )
                }
            }.map {
                it.findParameter()!!.accept(ParameterVisitor(ev))
            }.forEach(parameters::add)

            parameterFields.onEach(scope::addField).forEach(fields::add)

            val signature = FunctionSignature(scope.className, parameters, BuiltInType.VOID, false)
            val innerScope = Scope(scope)

            innerScope.addLocalVariable(LocalVariable("self", scope.classType))
            parameters.map { LocalVariable(it.name, it.type) }.forEach(innerScope::addLocalVariable)

            val function = Constructor(signature, Block(innerScope,
                fields.map {
                    Assignment(
                        null,
                        FieldReference(innerScope.getField(it.name)),
                        LocalVariableReference(innerScope.getLocalVariable(it.name)),
                        true,
                        CallingScope.CONSTRUCTOR
                    )
                }.toMutableList()
            ), ctorAccess, true)

            scope.addSignature(signature)
            functions += function
        }

        ctx.findClassBody()!!.findField().map {
            it.accept(fieldVisitor)
        }.onEach(scope::addField).onEach(fields::add)

        fields += ctx.findClassBody()!!.findFieldDeclaration().map {
            it.accept(fieldDeclarationVisitor)
        }.flatten().onEach(scope::addField)

        if (primaryCtorCtx != null)
            ctorCtx.map {
                it.findConstructorDeclaration()?.accept(functionSignatureVisitor)!!
            }.forEach(scope::addSignature)
        else if (ctorCtx.isNotEmpty())
            throw RuntimeException("Could not have auxiliary constructors without primary constructor.")


        val constructorExists = primaryCtorCtx != null || ctx.findClassBody()!!.findConstructor().isNotEmpty()

        if (!constructorExists) {
            val constructorSignature = FunctionSignature(name, listOf(), BuiltInType.VOID, false)

            scope.addSignature(constructorSignature)
        }

        functionsCtx.map {
            it.findFunctionDeclaration()?.accept(functionSignatureVisitor)!!
        }.forEach(scope::addSignature)

        functionsCtx.map {
            it.accept(FunctionVisitor(scope))
        }.forEach(functions::add)

        ctorCtx.forEach {
            functions += it.accept(FunctionVisitor(scope))
        }

        if (!constructorExists)
            functions += getDefaultConstructor()

        functions.sortWith { o1, _ ->
            if (o1 is Constructor) return@sortWith -1 else 1
        } // Moves constructors to front.

        return ClassDeclaration(name, functions, fields, accessModifier)
    }

    private fun getDefaultConstructor(): Constructor =
        Constructor(scope.getMethodCallSignatureWithoutParameters(scope.className), Block(scope), AccessModifier.PUB)
}