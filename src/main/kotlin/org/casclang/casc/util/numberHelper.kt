package org.casclang.casc.util

operator fun Number.plus(number: Number) =
    "${
        when (this) {
            is Double -> this + number.toDouble()
            is Float -> this + number.toFloat()
            is Long -> this + number.toLong()
            is Int -> this + number.toInt()
            else -> this.toInt() + number.toInt()
        }
    }"

operator fun Number.minus(number: Number) =
    "${
        when (this) {
            is Double -> this - number.toDouble()
            is Float -> this - number.toFloat()
            is Long -> this - number.toLong()
            is Int -> this - number.toInt()
            else -> this.toInt() - number.toInt()
        }
    }"

operator fun Number.times(number: Number) =
    "${
        when (this) {
            is Double -> this * number.toDouble()
            is Float -> this * number.toFloat()
            is Long -> this * number.toLong()
            is Int -> this * number.toInt()
            else -> this.toInt() * number.toInt()
        }
    }"

operator fun Number.div(number: Number) =
    "${
        when (this) {
            is Double -> this / number.toDouble()
            is Float -> this / number.toFloat()
            is Long -> this / number.toLong()
            is Int -> this / number.toInt()
            else -> this.toInt() / number.toInt()
        }
    }"