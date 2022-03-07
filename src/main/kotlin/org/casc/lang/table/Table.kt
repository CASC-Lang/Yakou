package org.casc.lang.table

import org.casc.lang.ast.File

/*
 Known as symbol table, used to store class compiled by CASC Compiler, the main idea of storing class objects here is:
 1. No checking/emitting is ahead of time
 2. No rely on JVM's reflection when it's already able to be known through internal runtime accessing
 That's it, this could avoid some unexpected effects caused by either threading under the hood or some B.S. (Bullshits)
*/
object Table {
    // Full qualified class name to File object
    var cachedClasses: Map<String, File> = mutableMapOf()


}