package io.github.chaosunity.casc.parsing.global

import io.github.chaosunity.casc.parsing.`class`.Function

import java.util

class ClassDeclaration(private var _name: String, private var _methods: util.List[Function]) {
    def name: String = _name

    def methods: util.List[Function] = _methods
}
