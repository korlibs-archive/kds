package com.soywiz.kds.specialized

expect class IntMap<T>() {
	val size: Int
	fun clear(): Unit
	fun remove(key: Int): Unit
	fun getKeys(): IntArray
	operator fun get(key: Int): T?
	operator fun set(key: Int, value: T): Unit
}

fun <T> IntMap<T>.getOrPut(key: Int, callback: () -> T): T {
	val res = get(key)
	if (res == null) set(key, callback())
	return get(key)!!
}