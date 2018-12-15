package com.soywiz.kds

class CacheMap<K, V>(
    val maxSize: Int = 16,
    val free: (K, V) -> Unit = { k, v -> }
) : MutableMap<K, V> {
    private val map: LinkedHashMap<K, V> = LinkedHashMap()

    override val size: Int get() = map.size

    override fun remove(key: K): V? {
        val value = map.remove(key)
        if (value != null) free(key, value)
        return value
    }

    override operator fun get(key: K) = map[key]
    override fun put(key: K, value: V): V? {
        if (size >= maxSize && !map.containsKey(key)) remove(map.keys.first())

        val oldValue = map[key]
        if (oldValue != value) {
            remove(key) // refresh if exists
            map[key] = value
        }
        return oldValue
    }

    inline fun getOrPut(key: K, callback: (K) -> V): V {
        if (key !in this) set(key, callback(key))
        return get(key)!!
    }

    override fun clear() {
        val keys = map.keys.toList()
        for (key in keys) remove(key)
    }

    override fun toString(): String = map.toString()

    override val entries: MutableSet<MutableMap.MutableEntry<K, V>> get() = map.entries
    override val keys: MutableSet<K> get() = map.keys
    override val values: MutableCollection<V> get() = map.values
    override fun containsKey(key: K): Boolean = map.containsKey(key)
    override fun containsValue(value: V): Boolean = map.containsValue(value)
    override fun isEmpty(): Boolean = map.isEmpty()
    override fun putAll(from: Map<out K, V>) = run { for ((k, v) in from) put(k, v) }
}
