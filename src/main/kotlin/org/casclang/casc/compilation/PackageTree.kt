package org.casclang.casc.compilation

import java.io.File

object PackageTree {
    // Map<Full Class path, CASCFile>
    val classes = mutableMapOf<String, CASCFile>()
    var sourceSetFolder: File? = null

    fun init(sourceSetFolder: File) {
        this.sourceSetFolder = sourceSetFolder
        addCASCFile(sourceSetFolder)
    }

    fun addCASCFile(file: File) {
        if (file.isFile) {
            val extension = file.extension

            if (extension != "casc" && extension != "cas")
                return

            val className = file.nameWithoutExtension
            val classPackage = File(file.absolutePath.removePrefix(sourceSetFolder!!.absolutePath)).parent.replace('\\', '/').removePrefix("/")

            classes += "${if (classPackage.isEmpty()) "" else "$classPackage/"}$className" to CASCFile(className, classPackage, extension)
        } else if (file.isDirectory) {
            file.listFiles()?.forEach(this::addCASCFile)
        }
    }
}