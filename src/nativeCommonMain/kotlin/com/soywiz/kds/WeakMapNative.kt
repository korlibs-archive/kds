package com.soywiz.kds

import kotlin.native.ref.*

actual class WeakMap<K : Any, V> {
	private val keys = ArrayList<WeakReference<K>>()
	private val values = ArrayList<V>()

	private var cleanupCounter = 0

	private fun doCleanup() {
		for (n in keys.size - 1 downTo 0) {
			if (keys[n].get() == null) {
				keys.removeAt(n)
				values.removeAt(n)
			}
		}
	}

	private fun gc() {
		if (cleanupCounter++ >= 1000) {
			cleanupCounter = 0
			doCleanup()
		}
	}

	private fun getIndex(key: K): Int {
		gc()
		for (n in 0 until keys.size) {
			if (keys[n].get() == key) return n
		}
		return -1
	}

	actual operator fun contains(key: K): Boolean = getIndex(key) >= 0
	actual operator fun set(key: K, value: V) {
		if (key is String) error("Can't use String as WeakMap keys")
		val index = getIndex(key)
		if (index >= 0) {
			keys[index] = WeakReference(key)
			values[index] = value
		} else {
			keys.add(WeakReference(key))
			values.add(value)
		}
	}
	actual operator fun get(key: K): V? {
		val index = getIndex(key)
		return if (index >= 0) values[index] else null
	}
}
