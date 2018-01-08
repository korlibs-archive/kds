package com.soywiz.kds.specialized

// @TODO: We should improve this!
open class IntMap<T>(private val loadFactor: Double) {
    constructor() : this(0.75)

    private var nbits = 4
    private var buckets = arrayOfNulls<Bucket<T>>(1 shl nbits)
    private val capacity: Int get() = buckets.size

    private class Bucket<T> {
        private val keys = IntArrayList()
        private val values = arrayListOf<T>()
        val size get() = keys.size

        fun clear() {
            keys.clear()
            values.clear()
        }

        fun getKeyIndex(key: Int) = keys.indexOf(key)

        fun getKeyAt(index: Int) = keys[index]
        fun getValueAt(index: Int) = values[index]

        fun add(key: Int, value: T) {
            keys.add(key)
            values.add(value)
        }

        fun setValueAt(index: Int, value: T): T {
            val old = values[index]
            values[index] = value
            return old
        }

        fun remove(key: Int): Boolean {
            val index = getKeyIndex(key)
            if (index < 0) return false
            keys.removeAt(index)
            values.removeAt(index)
            return true
        }
    }

    var size: Int = 0; private set

    fun clear(): Unit {
        for (bucket in buckets) bucket?.clear()
    }

    private fun _getBucket(key: Int, create: Boolean = false): Bucket<T>? {
        val hash = hash(key) and ((1 shl nbits) - 1)
        var bucket = buckets[hash]
        if (create && bucket == null) {
            bucket = Bucket()
            buckets[hash] = bucket
        }
        return bucket
    }

    private fun tryGetBucket(key: Int): Bucket<T>? = _getBucket(key, create = false)
    private fun getOrCreateBucket(key: Int): Bucket<T> = _getBucket(key, create = true)!!

    fun remove(key: Int): Unit {
        if (tryGetBucket(key)?.remove(key) == true) {
            size--
        }
    }

    fun getKeys(): IntArray {
        val out = IntArray(size)
        var pos = 0
        for (bucket in buckets) {
            val b = bucket ?: continue
            for (n in 0 until b.size) out[pos++] = b.getKeyAt(n)
        }
        return out
    }

    fun has(key: Int): Boolean {
        return (tryGetBucket(key) ?: return false).getKeyIndex(key) >= 0
    }

    operator fun get(key: Int): T? {
        val bucket = tryGetBucket(key) ?: return null
        val index = bucket.getKeyIndex(key)
        if (index < 0) return null
        return bucket.getValueAt(index)
    }

    operator fun set(key: Int, value: T): T? {
        val bucket = getOrCreateBucket(key)
        val index = bucket.getKeyIndex(key)
        // Do not exists
        if (index < 0) {
            if (size >= capacity * loadFactor) {
                rehash()
                return set(key, value)
            }
            bucket.add(key, value)
            size++
            return null
        } else {
            return bucket.setValueAt(index, value)
        }
    }

    private fun rehash() {
        val oldBuckets = this.buckets
        nbits++
        this.buckets = arrayOfNulls(1 shl nbits)
        //println("rehash: ${this.buckets.size}")
        for (ob in oldBuckets) {
            val b = ob ?: continue
            for (n in 0 until b.size) {
                set(b.getKeyAt(n), b.getValueAt(n))
            }
        }
    }

    private fun hash(value: Int) = value xor (-0x12477ce0)

    fun getOrPut(key: Int, callback: () -> T): T {
        val res = get(key)
        if (res == null) set(key, callback())
        return get(key)!!
    }
}
