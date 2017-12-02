package com.soywiz.kds.specialized

actual class IntMap<T> actual constructor() {
	// @TODO: Optimize
	val map = HashMap<Int, T>()

	actual val size: Int get() = map.size
	actual fun clear(): Unit = map.clear()
	actual fun remove(key: Int): Unit = run { map.remove(key) }
	actual operator fun get(key: Int): T? = map[key]
	actual operator fun set(key: Int, value: T): Unit = run { map[key] = value }
	actual fun getKeys(): IntArray = IntArrayList().apply { this += map.keys }.toIntArray()
}
