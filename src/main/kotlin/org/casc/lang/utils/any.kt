package org.casc.lang.utils

fun <T, R> T?.getOrElse(valueFunction: (T) -> R, value: R): R =
    if (this != null) valueFunction(this)
    else value

fun <T, R> T?.getOrElse(notNullValue: R, value: R): R =
    if (this != null) notNullValue
    else value

fun <T> T?.getOrElse(notNullValue: Int): Int =
    if (this != null) notNullValue
    else 0

fun <T> T?.getOrElse(): Boolean =
    this != null
