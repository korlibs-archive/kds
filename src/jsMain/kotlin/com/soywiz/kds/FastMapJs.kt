@file:Suppress("NOTHING_TO_INLINE")

package com.soywiz.kds

import com.soywiz.kds.internal.*

actual class FastIntMap<T>(dummy: Boolean)

actual fun <T> FastIntMap(): FastIntMap<T> = js("(new Map())")
actual val <T> FastIntMap<T>.size: Int get() = (this.asDynamic()).size
actual fun <T> FastIntMap<T>.keys(): List<Int> = Array_from((this.asDynamic()).keys()).unsafeCast<Array<Int>>().toList()
actual inline operator fun <T> FastIntMap<T>.get(key: Int): T? = (this.asDynamic()).get(key)
actual inline operator fun <T> FastIntMap<T>.set(key: Int, value: T): Unit = run { (this.asDynamic()).set(key, value) }
actual inline operator fun <T> FastIntMap<T>.contains(key: Int): Boolean = (this.asDynamic()).contains(key) != undefined
actual inline fun <T> FastIntMap<T>.remove(key: Int): Unit = run { (this.asDynamic()).delete(key) }
actual inline fun <T> FastIntMap<T>.removeRange(src: Int, dst: Int) {
	//@Suppress("UNUSED_VARIABLE") val obj = this.asDynamic()
	//js("for (var key in obj.keys()) if (key >= src && key <= dst) obj.delete(key);")
	for (key in keys) if (key in src..dst) remove(key)
}
actual inline fun <T> FastIntMap<T>.clear() {
	(this.asDynamic()).clear()
}

actual class FastStringMap<T>(dummy: Boolean)
//actual typealias FastStringMap<T> = Any<T>

actual fun <T> FastStringMap(): FastStringMap<T> = js("(new Map())")
actual val <T> FastStringMap<T>.size: Int get() = this.asDynamic().size
actual fun <T> FastStringMap<T>.keys(): List<String> = Array_from((this.asDynamic()).keys()).unsafeCast<Array<String>>().toList()
actual inline operator fun <T> FastStringMap<T>.get(key: String): T? = (this.asDynamic()).get(key)
actual inline operator fun <T> FastStringMap<T>.set(key: String, value: T): Unit = run { (this.asDynamic()).set(key, value) }
actual inline operator fun <T> FastStringMap<T>.contains(key: String): Boolean = (this.asDynamic()).has(key)
actual inline fun <T> FastStringMap<T>.remove(key: String): Unit = run { (this.asDynamic()).delete(key) }
actual inline fun <T> FastStringMap<T>.clear() = run { (this.asDynamic()).clear() }
