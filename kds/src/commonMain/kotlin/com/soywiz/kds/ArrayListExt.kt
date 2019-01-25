package com.soywiz.kds

inline fun IntRange.toIntList(): IntArrayList = IntArrayList(this.endInclusive - this.start).also { for (v in this.start .. this.endInclusive) it.add(v) }

inline fun Iterable<Int>.toIntList(): IntArrayList = IntArrayList().also { for (v in this) it.add(v) }
inline fun Iterable<Float>.toFloatList(): FloatArrayList = FloatArrayList().also { for (v in this) it.add(v) }
inline fun Iterable<Double>.toDoubleList(): DoubleArrayList = DoubleArrayList().also { for (v in this) it.add(v) }

//  MAP
inline fun <T> Iterable<T>.mapInt(callback: (T) -> Int): IntArrayList = IntArrayList().also { for (v in this) it.add(callback(v)) }
inline fun <T> Iterable<T>.mapFloat(callback: (T) -> Float): FloatArrayList = FloatArrayList().also { for (v in this) it.add(callback(v)) }
inline fun <T> Iterable<T>.mapDouble(callback: (T) -> Double): DoubleArrayList = DoubleArrayList().also { for (v in this) it.add(callback(v)) }

// FILTER
inline fun IntArrayList.filter(callback: (Int) -> Boolean): IntArrayList = IntArrayList().also { for (v in this) if (callback(v)) it.add(v) }
inline fun FloatArrayList.filter(callback: (Float) -> Boolean): FloatArrayList = FloatArrayList().also { for (v in this) if (callback(v)) it.add(v) }
inline fun DoubleArrayList.filter(callback: (Double) -> Boolean): DoubleArrayList = DoubleArrayList().also { for (v in this) if (callback(v)) it.add(v) }
