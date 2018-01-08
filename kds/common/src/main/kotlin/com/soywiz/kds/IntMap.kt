package com.soywiz.kds

class IntMap<T>(private val loadFactor: Double) {
    constructor() : this(0.75)

    private val i = IntMixedMap<T>(MixedArrayList.Shape.OBJ)

    val size: Int get() = i.size
    fun clear() = i.clear()
    fun remove(key: Int) = i.remove(key)
    fun has(key: Int): Boolean = i.has(key)
    fun getKeys() = i.getKeys()
    operator fun get(key: Int): T? = i.getObj(key)
    operator fun set(key: Int, value: T): T? = i.setObj(key, value)
}

fun <T> IntMap<T>.getOrPut(key: Int, callback: () -> T): T {
    val res = get(key)
    if (res == null) set(key, callback())
    return get(key)!!
}
