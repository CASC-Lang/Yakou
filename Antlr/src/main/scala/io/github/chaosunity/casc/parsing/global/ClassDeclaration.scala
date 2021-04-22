package io.github.chaosunity.casc.parsing.global

import io.github.chaosunity.casc.parsing.`class`.Function

class ClassDeclaration(private var _name: String, private var _methods: List[Function]) {
    def name: String = _name

    def methods: List[Function] = _methods
}
