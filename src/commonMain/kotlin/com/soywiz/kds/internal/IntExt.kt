package com.soywiz.kds.internal

import kotlin.math.*

internal infix fun Int.divCeil(that: Int): Int = if (this % that != 0) (this / that) + 1 else (this / that)

internal infix fun Int.umod(other: Int): Int {
    val remainder = this % other
    return when {
        remainder < 0 -> remainder + other
        else -> remainder
    }
}

// @TODO: Use bit counting instead
internal fun ilog2(v: Int): Int = log2(v.toDouble()).toInt()
