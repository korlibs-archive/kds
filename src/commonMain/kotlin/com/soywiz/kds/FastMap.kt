package com.soywiz.kds

expect class FastIntMap<T>

expect fun <T> FastIntMap(): FastIntMap<T>
expect operator fun <T> FastIntMap<T>.get(key: Int): T?
expect operator fun <T> FastIntMap<T>.set(key: Int, value: T): Unit
expect operator fun <T> FastIntMap<T>.contains(key: Int): Boolean
expect fun <T> FastIntMap<T>.remove(key: Int)
expect fun <T> FastIntMap<T>.removeRange(src: Int, dst: Int)
expect fun <T> FastIntMap<T>.clear()

////////////////////////////


expect class FastStringMap<T>

expect fun <T> FastStringMap(): FastStringMap<T>
expect fun <T> FastStringMap<T>.keys(): List<String>
expect operator fun <T> FastStringMap<T>.get(key: String): T?
expect operator fun <T> FastStringMap<T>.set(key: String, value: T): Unit
expect operator fun <T> FastStringMap<T>.contains(key: String): Boolean
expect fun <T> FastStringMap<T>.remove(key: String)
expect fun <T> FastStringMap<T>.clear()

fun <T> FastStringMap<T>.values(): List<T> = this.keys().map { this[it] } as List<T>
val <T> FastStringMap<T>.keys: List<String> get() = keys()
val <T> FastStringMap<T>.values: List<T> get() = values()

inline fun <T> FastStringMap<T>.getNull(key: String?): T? = if (key == null) null else get(key)

inline fun <T> FastStringMap<T>.getOrPut(key: String, callback: () -> T): T {
    val res = get(key)
    if (res != null) return res
    val out = callback()
    set(key, out)
    return out
}
