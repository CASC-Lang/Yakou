package io.github.chaosunity.casc.parsing

import io.github.chaosunity.casc.parsing.scope.AccessModifier
import io.github.chaosunity.casc.parsing.scope.Field

data class ClassDeclaration(val name: String, val functions: List<Function<*>>, val fields: List<Field>, val accessModifier: AccessModifier)