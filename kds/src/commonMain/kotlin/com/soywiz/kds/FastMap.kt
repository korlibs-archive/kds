package com.soywiz.kds

expect class FastIntMap<T>

expect fun <T> FastIntMap(): FastIntMap<T>
expect val <T> FastIntMap<T>.size: Int
expect fun <T> FastIntMap<T>.keys(): List<Int>
expect operator fun <T> FastIntMap<T>.get(key: Int): T?
expect operator fun <T> FastIntMap<T>.set(key: Int, value: T): Unit
expect operator fun <T> FastIntMap<T>.contains(key: Int): Boolean
expect fun <T> FastIntMap<T>.remove(key: Int)
expect fun <T> FastIntMap<T>.removeRange(src: Int, dst: Int)
expect fun <T> FastIntMap<T>.clear()

fun <T> FastIntMap<T>.values(): List<T> = this.keys().map { this[it] } as List<T>
val <T> FastIntMap<T>.keys: List<Int> get() = keys()
val <T> FastIntMap<T>.values: List<T> get() = values()

inline fun <T> FastIntMap<T>.getNull(key: Int?): T? = if (key == null) null else get(key)

inline fun <T> FastIntMap<T>.getOrPut(key: Int, callback: () -> T): T {
    val res = get(key)
    if (res != null) return res
    val out = callback()
    set(key, out)
    return out
}

////////////////////////////


expect class FastStringMap<T>

expect fun <T> FastStringMap(): FastStringMap<T>
expect val <T> FastStringMap<T>.size: Int
expect fun <T> FastStringMap<T>.keys(): List<String>
expect operator fun <T> FastStringMap<T>.get(key: String): T?
expect operator fun <T> FastStringMap<T>.set(key: String, value: T): Unit
expect operator fun <T> FastStringMap<T>.contains(key: String): Boolean
expect fun <T> FastStringMap<T>.remove(key: String)
expect fun <T> FastStringMap<T>.clear()

fun <T> FastStringMap<T>.values(): List<T> = this.keys().map { this[it] } as List<T>
val <T> FastStringMap<T>.keys: List<String> get() = keys()
val <T> FastStringMap<T>.values: List<T> get() = values()

expect inline fun <T> FastStringMap<T>.fastKeyForEach(callback: (key: String) -> Unit): Unit

inline fun <T> FastStringMap<T>.fastValueForEach(callback: (value: T?) -> Unit): Unit {
    fastKeyForEach { callback(this[it]) }
}
inline fun <T> FastStringMap<T>.fastForEach(callback: (key: String, value: T?) -> Unit): Unit {
    fastKeyForEach { callback(it, this[it]) }
}

inline fun <T> FastStringMap<T>.getNull(key: String?): T? = if (key == null) null else get(key)

inline fun <T> FastStringMap<T>.getOrPut(key: String, callback: () -> T): T {
    val res = get(key)
    if (res != null) return res
    val out = callback()
    set(key, out)
    return out
}
