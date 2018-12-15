package com.soywiz.kds

import com.soywiz.kds.internal.*

// GENERIC

/**
 * TGen growable ArrayList without boxing.
 */
@Suppress("UNCHECKED_CAST")
class TGenArrayList<TGen>(capacity: Int = 7) : Collection<TGen> {
    var data: Array<TGen> = arrayOfNulls<Any>(capacity) as Array<TGen>; private set
    internal val capacity: Int get() = data.size
    private var length: Int = 0
    override var size: Int
        get() = length
        set(value) {
            ensure(value)
            this.length = value
        }

    constructor(other: TGenArrayList<TGen>) : this() {
        add(other)
    }

    constructor(vararg other: TGen) : this() {
        add(other)
    }

    private fun ensure(count: Int) {
        if (length + count > data.size) {
            data = data.copyOf(kotlin.math.max(length + count, data.size * 3)) as Array<TGen>
        }
    }

    fun clear() = run { length = 0 }

    fun add(value: TGen) {
        ensure(1)
        data[length++] = value
    }

    operator fun plusAssign(value: TGen) = add(value)
    operator fun plusAssign(value: Array<TGen>) = add(value)
    operator fun plusAssign(value: TGenArrayList<TGen>) = add(value)
    operator fun plusAssign(value: Iterable<TGen>) = add(value)

    fun add(values: Array<out TGen>, offset: Int = 0, length: Int = values.size) {
        ensure(values.size)
        arraycopy(values as Array<TGen>, offset, data, this.size, length)
        this.size += values.size
    }

    fun add(values: TGenArrayList<TGen>) = add(values.data, 0, values.size)
    fun add(values: Iterable<TGen>) = run { for (v in values) add(v) }

    operator fun get(index: Int): TGen = data[index]

    operator fun set(index: Int, value: TGen) = run {
        if (index >= length) {
            ensure(index + 1)
            length = index + 1
        }
        data[index] = value
    }

    override fun iterator(): Iterator<TGen> = data.take(length).iterator()

    override fun contains(element: TGen): Boolean {
        for (n in 0 until length) if (this.data[n] == element) return true
        return false
    }

    override fun containsAll(elements: Collection<TGen>): Boolean {
        for (e in elements) if (!contains(e)) return false
        return true
    }

    @Suppress("ReplaceSizeZeroCheckWithIsEmpty")
    override fun isEmpty(): Boolean = this.size == 0

    fun indexOf(value: TGen, start: Int = 0, end: Int = this.size): Int {
        for (n in start until end) if (data[n] == value) return n
        return -1
    }

    fun removeAt(index: Int): TGen {
        if (index < 0 || index >= length) throw IndexOutOfBoundsException()
        val out = data[index]
        if (index < length - 1) arraycopy(data, index + 1, data, index, length - index - 1)
        length--
        return out
    }

    fun toTGenArray() = this.data.copyOf(length)
}

fun <TGen> tgenArrayListOf(vararg values: TGen) = TGenArrayList<TGen>(*values)
