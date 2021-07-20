package org.casclang.casc.parsing

import org.casclang.casc.parsing.scope.AccessModifier
import org.casclang.casc.parsing.scope.Field
import org.casclang.casc.parsing.scope.Scope

data class ClassDeclaration(
    val name: String,
    val scope: Scope,
    val functions: List<Function<*>>,
    val fields: List<Field>,
    val implementations: List<Implementation>,
    val accessModifier: AccessModifier
)