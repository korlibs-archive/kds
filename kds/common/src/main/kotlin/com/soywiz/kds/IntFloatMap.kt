package com.soywiz.kds

class IntFloatMap {
    private val i = IntIntMap()

    val size: Int get() = i.size
    fun clear() = i.clear()
    fun remove(key: Int) = i.remove(key)
    fun has(key: Int): Boolean = i.has(key)
    fun getKeys() = i.getKeys()
    operator fun get(key: Int): Float = Float.fromBits(i[key])
    operator fun set(key: Int, value: Float): Float = Float.fromBits(i.set(key, value.toRawBits()))
}