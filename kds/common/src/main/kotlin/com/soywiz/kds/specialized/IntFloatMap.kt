package com.soywiz.kds.specialized

class IntFloatMap {
    private val i = IntMixedMap<Int>(MixedArrayList.Shape.INT)

    val size: Int get() = i.size
    fun clear() = i.clear()
    fun remove(key: Int) = i.remove(key)
    fun has(key: Int): Boolean = i.has(key)
    fun getKeys() = i.getKeys()
    operator fun get(key: Int): Float = Float.fromBits(i.getInt(key))
    operator fun set(key: Int, value: Float): Int = i.setInt(key, value.toRawBits())
}