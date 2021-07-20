package org.casclang.casc.compilation

object Compiler {
    lateinit var compilation: Compilation

    fun init(args: Array<String>): Compiler {
        compilation = Compilation(args)
        return this
    }

    fun compile() {
        if (compilation.source.isFile) {
            PackageTree.sourceSetFolder = compilation.source.parentFile
            PackageTree.addCASCFile(compilation.source)
            Parser(compilation.source.absolutePath).parseFile().emitBytecode()
        } else if (compilation.source.isDirectory) {
            PackageTree.init(compilation.source)
            PackageTree.classes.forEach {
                if (!PackageTree.classes[it.key]!!.isCompiled) {
                    Parser(it.value.absoluteFilePath)
                        .parseFile()
                        .emitBytecode()
                    it.value.isCompiled = true
                }
            }
        }
    }
}