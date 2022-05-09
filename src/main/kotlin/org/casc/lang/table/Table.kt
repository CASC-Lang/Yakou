package org.casc.lang.table

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
import org.casc.lang.ast.ClassInstance
import org.casc.lang.ast.File
import org.casc.lang.ast.TraitInstance
import org.casc.lang.ast.TypeInstance

/*
 Known as symbol table, used to store class compiled by CASC Compiler, the main idea of storing class objects here is:
 1. No checking/emitting is ahead of time
 2. No rely on JVM's reflection when it's already able to be known through internal runtime accessing
 That's it, this could avoid some unexpected effects caused by either threading under the hood or some B.S. (Bullshits)
*/
object Table {
    // Full qualified class name to File object
    var cachedClasses: Object2ObjectOpenHashMap<Reference, File> = Object2ObjectOpenHashMap()

    /**
     * By given full qualified class path (including package path) will return a ClassType if exists or null
     */
    fun findType(classReference: Reference): ClassType? =
        cachedClasses[classReference]?.let {
            val typeInstance = it.typeInstance

            ClassType(
                classReference.fullQualifiedPath,
                if (typeInstance is ClassInstance && typeInstance.packageReference != null)
                    typeInstance.packageReference!!.fullQualifiedPath
                else Reference.OBJECT_TYPE_REFERENCE.fullQualifiedPath,
                it.typeInstance.accessor,
                if (typeInstance is ClassInstance) typeInstance.mutKeyword != null
                else false,
                typeInstance is TraitInstance
            )
        }

    fun findTypeInstance(classReference: Reference): TypeInstance? =
        cachedClasses[classReference]?.typeInstance

    fun hasClass(classReference: Reference): Boolean =
        cachedClasses.containsKey(classReference)
}