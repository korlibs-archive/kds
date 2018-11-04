package com.soywiz.kds.internal

internal object MemTools {
	fun <T> arraycopy(src: Array<T>, srcPos: Int, dst: Array<T>, dstPos: Int, size: Int) {
		src.copyInto(dst, dstPos, srcPos, srcPos + size)
	}

	fun arraycopy(src: IntArray, srcPos: Int, dst: IntArray, dstPos: Int, size: Int) {
		src.copyInto(dst, dstPos, srcPos, srcPos + size)
	}

	fun arraycopy(src: FloatArray, srcPos: Int, dst: FloatArray, dstPos: Int, size: Int) {
		src.copyInto(dst, dstPos, srcPos, srcPos + size)
	}

	fun arraycopy(src: DoubleArray, srcPos: Int, dst: DoubleArray, dstPos: Int, size: Int) {
		src.copyInto(dst, dstPos, srcPos, srcPos + size)
	}

	fun <T> fill(array: Array<T>, value: T) = run { for (n in 0 until array.size) array[n] = value }
	fun fill(array: IntArray, value: Int) = run { for (n in 0 until array.size) array[n] = value }
}