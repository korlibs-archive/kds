package com.soywiz.kds

inline fun <reified T> mapWhile(cond: () -> Boolean, gen: (Int) -> T): Array<T> =
    arrayListOf<T>().apply { while (cond()) this += gen(this.size) }.toTypedArray()

fun mapWhileInt(cond: () -> Boolean, gen: (Int) -> Int): IntArray =
    IntArrayList().apply { this += gen(this.size) }.toIntArray()

fun mapWhileFloat(cond: () -> Boolean, gen: (Int) -> Float): FloatArray =
    FloatArrayList().apply { while (cond()) this += gen(this.size) }.toFloatArray()

fun mapWhileDouble(cond: () -> Boolean, gen: (Int) -> Double): DoubleArray =
    DoubleArrayList().apply { while (cond()) this += gen(this.size) }.toDoubleArray()
