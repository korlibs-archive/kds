package com.soywiz.kds

import kotlin.native.ref.*

actual typealias FastIntMap<T> = IntMap<T>

actual inline fun <T> FastIntMap(): FastIntMap<T> = IntMap()
actual val <T> FastIntMap<T>.size: Int get() = (this as IntMap<T>).size
actual fun <T> FastIntMap<T>.keys(): List<Int> = (this as IntMap<T>).keys.toList()
actual inline operator fun <T> FastIntMap<T>.get(key: Int): T? = (this as IntMap<T>).get(key)
actual inline operator fun <T> FastIntMap<T>.set(key: Int, value: T): Unit = run { (this as IntMap<T>).set(key, value) }
actual inline operator fun <T> FastIntMap<T>.contains(key: Int): Boolean = (this as IntMap<T>).contains(key)
actual inline fun <T> FastIntMap<T>.remove(key: Int): Unit = run { (this as IntMap<T>).remove(key) }
actual inline fun <T> FastIntMap<T>.removeRange(src: Int, dst: Int) = (this as IntMap<T>).removeRange(src, dst)
actual inline fun <T> FastIntMap<T>.clear() = (this as IntMap<T>).clear()

actual class FastStringMap<T>(val dummy: Boolean) {
    val map = LinkedHashMap<String, T>()
}

actual inline fun <T> FastStringMap(): FastStringMap<T> = FastStringMap(true)
actual val <T> FastStringMap<T>.size: Int get() = (this.map).size
actual inline operator fun <T> FastStringMap<T>.get(key: String): T? = (this.map).get(key)
actual inline operator fun <T> FastStringMap<T>.set(key: String, value: T): Unit = run { (this.map).set(key, value) }
actual inline operator fun <T> FastStringMap<T>.contains(key: String): Boolean = (this.map).contains(key)
actual inline fun <T> FastStringMap<T>.remove(key: String): Unit = run { (this.map).remove(key) }
actual inline fun <T> FastStringMap<T>.clear() = (this.map).clear()
actual fun <T> FastStringMap<T>.keys(): List<String> = map.keys.toList()

actual inline fun <T> FastStringMap<T>.fastKeyForEach(callback: (key: String) -> Unit): Unit {
    for (key in this.keys()) {
        callback(key)
    }
}

// @TODO: use a IntFastMap using the hash of the key to reduce the complexity on big collections.
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
