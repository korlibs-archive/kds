package com.soywiz.kds

@JsName("Map")
external private class JsMap<T> {
	fun delete(key: dynamic): Unit
	fun set(key: dynamic, value: T): T
	fun get(key: dynamic): T?
}

actual class IntMap<T> actual constructor() {
	private val map = JsMap<T>()
	actual fun remove(key: Int): Unit = map.delete(key)
	actual operator fun get(key: Int): T? = map.get(key)
	actual operator fun set(key: Int, value: T): Unit {
		map.set(key, value)
	}
}
