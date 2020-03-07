package com.soywiz.kds

import kotlin.reflect.*

interface Extra {
    var extra: LinkedHashMap<String, Any?>?

    class Mixin(override var extra: LinkedHashMap<String, Any?>? = null) : Extra

    @Suppress("UNCHECKED_CAST", "NOTHING_TO_INLINE")

    class Property<T : Any?>(val name: String? = null, val defaultGen: () -> T) {
        inline operator fun getValue(thisRef: Extra, property: KProperty<*>): T {
            val res = (thisRef.extra?.get(name ?: property.name) as T?)
            if (res == null) {
                val r = defaultGen()
                setValue(thisRef, property, r)
                return r
            }
            return res
        }

        inline operator fun setValue(thisRef: Extra, property: KProperty<*>, value: T): Unit = run {
            //beforeSet(value)
            thisRef.setExtra(name ?: property.name, value as Any?)
            //afterSet(value)
        }
    }

    class PropertyThis<in T2 : Extra, T : Any?>(val name: String? = null, val defaultGen: T2.() -> T) {
        inline operator fun getValue(thisRef: T2, property: KProperty<*>): T {
            val res = (thisRef.extra?.get(name ?: property.name) as T?)
            if (res == null) {
                val r = defaultGen(thisRef)
                setValue(thisRef, property, r)
                return r
            }
            return res
        }

        inline operator fun setValue(thisRef: T2, property: KProperty<*>, value: T): Unit = run {
            //beforeSet(value)
            if (thisRef.extra == null) thisRef.extra = LinkedHashMap()
            thisRef.extra?.set(name ?: property.name, value as Any?)
            //afterSet(value)
        }
    }
}

fun <T : Any> Extra.getExtraTyped(name: String): T? = extra?.get(name) as T?
fun Extra.getExtra(name: String): Any? = extra?.get(name)
fun Extra.setExtra(name: String, value: Any?): Unit {
    if (extra == null) extra = LinkedHashMap()
    extra?.set(name, value)
}

inline fun <T> extraProperty(name: String? = null, noinline default: () -> T) = Extra.Property(name, default)
/*
@Suppress("UNCHECKED_CAST", "NOTHING_TO_INLINE")
class extraProperty<T : Any?>(val name: String? = null, val default: () -> T) {
    inline operator fun getValue(thisRef: Extra, property: KProperty<*>): T {
        if (thisRef.extra == null) thisRef.extra = LinkedHashMap()
        return (thisRef.extra!!.getOrPut(name ?: property.name) { default() } as T)
    }

    inline operator fun setValue(thisRef: Extra, property: KProperty<*>, value: T): Unit = run {
        if (thisRef.extra == null) thisRef.extra = LinkedHashMap()
        thisRef.extra!![name ?: property.name] = value as Any?
    }
}
 */

class Computed<K : Computed.WithParent<K>, T>(val prop: KProperty1<K, T?>, val default: () -> T) {
    interface WithParent<T> {
        val parent: T?
    }

    operator fun getValue(thisRef: K?, p: KProperty<*>): T {
        var current: K? = thisRef
        while (current != null) {
            val result = prop.get(current)
            if (result != null) return result
            current = current.parent
        }

        return default()
    }
}

class WeakProperty<V>(val gen: () -> V) {
    val map = WeakMap<Any, V>()

    operator fun getValue(obj: Any, property: KProperty<*>): V = map.getOrPut(obj) { gen() }
    operator fun setValue(obj: Any, property: KProperty<*>, value: V) = run { map[obj] = value }
}

class WeakPropertyThis<T : Any, V>(val gen: T.() -> V) {
    val map = WeakMap<T, V>()

    operator fun getValue(obj: T, property: KProperty<*>): V = map.getOrPut(obj) { gen(obj) }
    operator fun setValue(obj: T, property: KProperty<*>, value: V) = run { map[obj] = value }
}
