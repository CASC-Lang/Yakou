package io.github.chaosunity.casc.util

import io.github.chaosunity.casc.compilation.Compiler
import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.jar.Attributes
import java.util.jar.JarEntry
import java.util.jar.JarOutputStream
import java.util.jar.Manifest

object JarFactory {
    fun create() {
        val file = File("${Compiler.compilation.outputDirectory.parent}/output.jar")

        if (file.exists()) file.delete()

        val manifest = Manifest()
        manifest.mainAttributes[Attributes.Name.MANIFEST_VERSION] = "1.0"
        val target = JarOutputStream(FileOutputStream(file.absolutePath), manifest)
        add(Compiler.compilation.outputDirectory, target)
        target.close()
    }

    private fun add(source: File, target: JarOutputStream) {
        var input: BufferedInputStream? = null

        try {
            if (source.isDirectory) {
                var name = source.path.replace('\\', '/')

                if (name.isNotEmpty()) {
                    if (!name.endsWith('/')) name += '/'

                    val entry = JarEntry(name)
                    entry.time = source.lastModified()
                    target.putNextEntry(entry)
                    target.closeEntry()
                }

                source.listFiles()?.forEach { add(it, target) }
            }

            val entry = JarEntry(source.path.replace('\\', '/'))
            entry.time = source.lastModified()
            target.putNextEntry(entry)
            input = BufferedInputStream(FileInputStream(source))

            val buffer = ByteArray(1024)

            while (true) {
                val count = input.read(buffer)

                if (count == -1) break

                target.write(buffer, 0 , count)
            }

            target.closeEntry()
        } finally {
            input?.close()
        }
    }
}