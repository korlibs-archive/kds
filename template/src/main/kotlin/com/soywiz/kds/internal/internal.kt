package com.soywiz.kds.internal

internal external infix fun Int.divCeil(that: Int): Int
internal external infix fun Int.umod(other: Int): Int
internal external fun ilog2(v: Int): Int

internal external fun <T> arraycopy(src: Array<T>, srcPos: Int, dst: Array<T>, dstPos: Int, size: Int)
internal external fun arraycopy(src: IntArray, srcPos: Int, dst: IntArray, dstPos: Int, size: Int)
internal external fun arraycopy(src: FloatArray, srcPos: Int, dst: FloatArray, dstPos: Int, size: Int)
internal external fun arraycopy(src: DoubleArray, srcPos: Int, dst: DoubleArray, dstPos: Int, size: Int)
internal external fun <T> Array<T>.fill(value: T)
internal external fun IntArray.fill(value: Int)

