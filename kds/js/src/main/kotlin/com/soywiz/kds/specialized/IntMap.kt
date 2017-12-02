package com.soywiz.kds.specialized

@JsName("Map")
external private class JsMap<T> {
	val size: Int
	fun clear(): Unit
	fun delete(key: dynamic): Unit
	fun set(key: dynamic, value: T): T
	fun get(key: dynamic): T?
}

@JsName("Array")
external private class JsArray {
	companion object {
		fun from(value: dynamic): dynamic
	}
}

actual class IntMap<T> actual constructor() {
	private val map = JsMap<T>()

	actual val size: Int get() = map.size
	actual fun clear(): Unit = map.clear()
	actual fun remove(key: Int): Unit = map.delete(key)
	actual operator fun get(key: Int): T? = map.get(key)
	actual operator fun set(key: Int, value: T): Unit = run { map.set(key, value) }
	@Suppress("UnsafeCastFromDynamic")
	actual fun getKeys(): IntArray {
		val array = JsArray.from(map.asDynamic().keys())
		val len: Int = array.length
		val out = IntArray(len)
		for (n in 0 until len) out[n] = array[n]
		return out
	}
}
