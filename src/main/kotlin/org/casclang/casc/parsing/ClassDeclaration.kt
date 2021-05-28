package org.casclang.casc.parsing

import org.casclang.casc.parsing.scope.AccessModifier
import org.casclang.casc.parsing.scope.Field

data class ClassDeclaration(
    val name: String,
    val functions: List<Function<*>>,
    val fields: List<Field>,
    val accessModifier: AccessModifier
)