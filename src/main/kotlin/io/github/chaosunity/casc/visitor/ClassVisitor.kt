package io.github.chaosunity.casc.visitor

import io.github.chaosunity.antlr.CASCBaseListener
import io.github.chaosunity.antlr.CASCBaseVisitor
import io.github.chaosunity.antlr.CASCParser
import io.github.chaosunity.casc.parsing.global.ClassDeclaration
import io.github.chaosunity.casc.parsing.scope.Scope

// TODO
class ClassVisitor : CASCBaseVisitor<ClassDeclaration>() {
    private lateinit var scope: Scope

    override fun visitClassDeclaration(ctx: CASCParser.ClassDeclarationContext?): ClassDeclaration {
        val name = ctx?.className()?.text
        val
    }
}