package io.github.chaosunity.casc.visitor

import io.github.chaosunity.casc.CASCBaseVisitor
import io.github.chaosunity.casc.CASCParser
import io.github.chaosunity.casc.parsing.ClassDeclaration
import io.github.chaosunity.casc.parsing.Constructor
import io.github.chaosunity.casc.parsing.MetaData
import io.github.chaosunity.casc.parsing.node.statement.Block
import io.github.chaosunity.casc.parsing.scope.AccessModifier
import io.github.chaosunity.casc.parsing.scope.FunctionSignature
import io.github.chaosunity.casc.parsing.scope.Scope
import io.github.chaosunity.casc.parsing.type.BuiltInType

class ClassVisitor : CASCBaseVisitor<ClassDeclaration>() {
    private lateinit var scope: Scope

    override fun visitClassDeclaration(ctx: CASCParser.ClassDeclarationContext): ClassDeclaration {
        val accessModifier = AccessModifier.getModifier(ctx.findOuterAccessMods()?.text)
        val name = ctx.findClassName()?.text!!
        val metadata = MetaData(name, "java.lang.Object")

        scope = Scope(metadata)

        val fieldVisitor = FieldVisitor(scope)
        val fieldDeclarationVisitor = FieldDeclarationVisitor(scope)
        val functionSignatureVisitor = FunctionSignatureVisitor(scope)
        val ctorCtx = ctx.findClassBody()?.findConstructor()
        val methodsCtx = ctx.findClassBody()?.findFunction()
        val fields = ctx.findClassBody()?.findField()?.map {
            it.accept(fieldVisitor)
        }?.onEach(scope::addField)?.toMutableList() ?: mutableListOf()

        fields += ctx.findClassBody()?.findFieldDeclaration()?.map {
            it.accept(fieldDeclarationVisitor)
        }?.flatten()?.onEach(scope::addField) ?: listOf()
        ctorCtx?.map {
            it.findConstructorDeclaration()?.accept(functionSignatureVisitor)!!
        }?.forEach(scope::addSignature)

        val constructorExists = ctx.findClassBody()?.findConstructor()?.isNotEmpty() ?: false

        if (!constructorExists) {
            val constructorSignature = FunctionSignature(name, listOf(), BuiltInType.VOID, false)

            scope.addSignature(constructorSignature)
        }

        methodsCtx?.map {
            it.findFunctionDeclaration()?.accept(functionSignatureVisitor)!!
        }?.forEach(scope::addSignature)

        val methods = methodsCtx?.map {
            it.accept(FunctionVisitor(scope))
        }?.toMutableList() ?: mutableListOf()

        ctorCtx?.forEach {
            methods += it.accept(FunctionVisitor(scope))
        }

        if (!constructorExists) {
            methods += getDefaultConstructor()
        }

        return ClassDeclaration(name, methods, fields, accessModifier)
    }

    private fun getDefaultConstructor(): Constructor =
        Constructor(scope.getMethodCallSignatureWithoutParameters(scope.className), Block(scope), AccessModifier.PUB)
}