package org.casclang.casc.util

/**
 *  Append a dot to lhe end of string when string is not empty.
 */
fun String.dot() =
    if (isNotEmpty()) "$this." else this