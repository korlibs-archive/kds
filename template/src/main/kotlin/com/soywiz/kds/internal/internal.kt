package com.soywiz.kds.internal

internal object MemTools {
    external fun <T> arraycopy(src: Array<T>, srcPos: Int, dst: Array<T>, dstPos: Int, size: Int)
    external fun arraycopy(src: IntArray, srcPos: Int, dst: IntArray, dstPos: Int, size: Int)
    external fun arraycopy(src: FloatArray, srcPos: Int, dst: FloatArray, dstPos: Int, size: Int)
    external fun arraycopy(src: DoubleArray, srcPos: Int, dst: DoubleArray, dstPos: Int, size: Int)
    external fun <T> fill(array: Array<T>, value: T)
    external fun fill(array: IntArray, value: Int)
}
