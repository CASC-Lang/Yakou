package org.casclang.casc.compilation

import java.io.File
import java.net.URL
import java.net.URLClassLoader

class Compilation(private val args: Array<String>) {
    val devMode: Boolean = args.contains("--dev")
    val outputDirectory: File = if (args.contains("-o")) {
        val path = File(args[args.indexOf("-o") + 1])

        if (path.isFile)
            throw IllegalArgumentException("Output path cannot be a file.")

        path
    } else {
        File("${System.getProperty("user.dir")}/build/casc/out")
    }.also(File::mkdirs)
    val source = File(args.lastOrNull() ?: throw RuntimeException("Could not find any valid source path for compilation.")).also {
        if (it.isFile && !(it.extension == "casc" || it.extension == "cas"))
            throw RuntimeException("Provided file '${it.name}' is not casc source file. Extension should be .casc or .cas.")
    }

    init {
        if (args.contains("-cp"))
            addClasspath(args.indexOf("-cp"))
    }

    private fun addClasspath(startIndex: Int) {
        var index = startIndex

        while (true) {
            index++

            val currentArg = args[index]
            val hasNextPath = currentArg.endsWith(';')
            val filePath = currentArg.removeSuffix(";")
            val file = File(if (filePath.endsWith('*')) filePath.removeSuffix("*") else filePath)

            val addURLMethod = URLClassLoader::class.java
                .getDeclaredMethod("addURL", URL::class.java)
            addURLMethod.isAccessible = true

            if (file.isDirectory) {
                val jarAndClasses = file.listFiles()?.filter {
                    it.extension == "jar" || it.extension == "class"
                } ?: listOf()

                jarAndClasses.forEach {
                    addURLMethod.invoke(ClassLoader.getSystemClassLoader(), it.toURI().toURL())
                }
            }

            if (file.isFile) {
                addURLMethod.invoke(ClassLoader.getSystemClassLoader(), file.toURI().toURL())
            }

            if (!hasNextPath) break
        }
    }
}