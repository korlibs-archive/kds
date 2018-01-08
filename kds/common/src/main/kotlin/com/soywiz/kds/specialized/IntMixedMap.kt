package com.soywiz.kds.specialized

// @TODO: We should improve this!
internal class IntMixedMap<T>(val shape: MixedArrayList.Shape, private val loadFactor: Double = 0.75) {
    private var nbits = 4
    private var buckets = arrayOfNulls<Bucket<T>>(1 shl nbits)
    private val capacity: Int get() = buckets.size
    private var resizeCapacity: Int = (this.buckets.size * loadFactor).toInt()

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

    fun getObj(key: Int): T? {
        val bucket = tryGetBucket(key) ?: return null
        val index = bucket.getKeyIndex(key)
        if (index < 0) return null
        return bucket.getObjectValueAt(index)
    }

    fun getInt(key: Int): Int {
        val bucket = tryGetBucket(key) ?: return 0
        val index = bucket.getKeyIndex(key)
        if (index < 0) return 0
        return bucket.getIntValueAt(index)
    }

    fun setObj(key: Int, value: T): T? {
        rehashIfRequired()
        val bucket = getOrCreateBucket(key)
        val index = bucket.getKeyIndex(key)
        // Do not exists
        if (index < 0) {
            bucket.addObj(key, value)
            size++
            return null
        } else {
            return bucket.setValueObjectAt(index, value)
        }
    }

    fun setInt(key: Int, value: Int): Int {
        rehashIfRequired()
        val bucket = getOrCreateBucket(key)
        val index = bucket.getKeyIndex(key)
        // Do not exists
        if (index < 0) {
            if (size >= capacity * loadFactor) {
                rehash()
                return setInt(key, value)
            }
            bucket.addInt(key, value)
            size++
            return 0
        } else {
            return bucket.setValueIntAt(index, value)
        }
    }

    private fun rehashIfRequired() {
        if (size >= resizeCapacity) {
            rehash()
        }
    }

    private fun rehash() {
        val oldBuckets = this.buckets
        nbits++
        this.buckets = arrayOfNulls(1 shl nbits)
        resizeCapacity = (this.buckets.size * loadFactor).toInt()
        //println("rehash: ${this.buckets.size}")
        for (ob in oldBuckets) {
            val b = ob ?: continue
            when (shape) {
                MixedArrayList.Shape.OBJ -> run { for (n in 0 until b.size) setObj(b.getKeyAt(n), b.getObjectValueAt(n)) }
                MixedArrayList.Shape.INT -> run { for (n in 0 until b.size) setInt(b.getKeyAt(n), b.getIntValueAt(n)) }
            }

        }
    }

    private fun hash(value: Int) = value xor (-0x12477ce0)

    private inner class Bucket<T> {
        private val keys = IntArrayList()
        private val values = MixedArrayList<T>(shape)
        val size get() = keys.size

        fun clear() {
            keys.clear()
            values.clear()
        }

        fun getKeyIndex(key: Int) = keys.indexOf(key)

        fun getKeyAt(index: Int) = keys[index]
        fun getObjectValueAt(index: Int) = values.objectGet(index)
        fun getIntValueAt(index: Int) = values.intGet(index)

        fun addObj(key: Int, value: T) {
            keys.add(key)
            values.objectAdd(value)
        }

        fun addInt(key: Int, value: Int) {
            keys.add(key)
            values.intAdd(value)
        }

        fun setValueObjectAt(index: Int, value: T): T {
            val old = values.objectGet(index)
            values.objectSet(index, value)
            return old
        }

        fun setValueIntAt(index: Int, value: Int): Int {
            val old = values.intGet(index)
            values.intSet(index, value)
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
}
