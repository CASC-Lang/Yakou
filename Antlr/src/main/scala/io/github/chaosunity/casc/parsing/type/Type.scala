package io.github.chaosunity.casc.parsing.`type`

trait Type {
    var name: String

    var `type`: Class[_]

    var descriptor: String

    var internalName: String
}
