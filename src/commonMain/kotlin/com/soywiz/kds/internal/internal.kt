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

internal fun <T> arraycopy(src: Array<T>, srcPos: Int, dst: Array<T>, dstPos: Int, size: Int) = src.copyInto(dst, dstPos, srcPos, srcPos + size)
internal fun arraycopy(src: IntArray, srcPos: Int, dst: IntArray, dstPos: Int, size: Int) = src.copyInto(dst, dstPos, srcPos, srcPos + size)
internal fun arraycopy(src: FloatArray, srcPos: Int, dst: FloatArray, dstPos: Int, size: Int) = src.copyInto(dst, dstPos, srcPos, srcPos + size)
internal fun arraycopy(src: DoubleArray, srcPos: Int, dst: DoubleArray, dstPos: Int, size: Int) = src.copyInto(dst, dstPos, srcPos, srcPos + size)
internal fun <T> Array<T>.fill(value: T) = run { for (n in 0 until this.size) this[n] = value }
internal fun IntArray.fill(value: Int) = run { for (n in 0 until this.size) this[n] = value }

internal typealias IntIterable = Iterable<Int>
internal typealias IntCollection = Collection<Int>
internal typealias IntMutableCollection = MutableCollection<Int>
internal typealias IntMutableIterator = MutableIterator<Int>

internal typealias FloatIterable = Iterable<Float>
internal typealias FloatCollection = Collection<Float>
internal typealias FloatMutableCollection = MutableCollection<Float>
internal typealias FloatMutableIterator = MutableIterator<Float>

internal typealias DoubleIterable = Iterable<Double>
internal typealias DoubleCollection = Collection<Double>
internal typealias DoubleMutableCollection = MutableCollection<Double>
internal typealias DoubleMutableIterator = MutableIterator<Double>
