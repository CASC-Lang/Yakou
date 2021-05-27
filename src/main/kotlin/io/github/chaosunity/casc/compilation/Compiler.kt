package io.github.chaosunity.casc.compilation

object Compiler {
    lateinit var compilation: Compilation

    fun init(args: Array<String>): Compiler {
        compilation = Compilation(args)
        return this
    }

    fun compile() {
        if (compilation.source.isFile) {
            val absolutePath = compilation.source.absolutePath
            Parser(absolutePath).parseFile().emitBytecode()
        } else if (compilation.source.isDirectory) {
            PackageTree.init(compilation.source)
            println(PackageTree.classes)
            PackageTree.classes.forEach {
                Parser("${compilation.source.path}/${it.value.relativeFilePath}")
                    .parseFile()
                    .emitBytecode()
                it.value.isCompiled = true
            }
        }
    }
}