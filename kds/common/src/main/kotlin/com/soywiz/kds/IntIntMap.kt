package com.soywiz.kds

class IntIntMap {
    private val i = IntMixedMap<Int>(MixedArrayList.Shape.INT)

    val size: Int get() = i.size
    fun clear() = i.clear()
    fun remove(key: Int) = i.remove(key)
    fun has(key: Int): Boolean = i.has(key)
    fun getKeys() = i.getKeys()
    operator fun get(key: Int): Int = i.getInt(key)
    operator fun set(key: Int, value: Int): Int = i.setInt(key, value)
}
