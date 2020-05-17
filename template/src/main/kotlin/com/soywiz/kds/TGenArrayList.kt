package com.soywiz.kds

import com.soywiz.kds.internal.*

// GENERIC

/**
 * TGen growable ArrayList without boxing.
 */
@Suppress("UNCHECKED_CAST")
class TGenArrayList<TGen>(capacity: Int = 7) : List<TGen> {
    companion object
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
        ensure(length)
        arraycopy(values as Array<TGen>, offset, data, this.size, length)
        this.size += length
    }

    fun add(values: TGenArrayList<TGen>) = add(values.data, 0, values.size)
    fun add(values: Iterable<TGen>) = run { for (v in values) add(v) }

    @Deprecated("Try to use getAt instead to prevent boxing", ReplaceWith("getAt(index)"))
    override operator fun get(index: Int): TGen = getAt(index)

    /** Gets an item of the list without boxing */
    fun getAt(index: Int): TGen = data[index]

    fun setAt(index: Int, value: TGen): TGen = value.also { set(index, value) }

    operator fun set(index: Int, value: TGen) = run {
        if (index >= length) {
            ensure(index + 1)
            length = index + 1
        }
        data[index] = value
    }

    override fun iterator(): Iterator<TGen> = listIterator(0)

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

    fun lastIndexOf(value: TGen, start: Int = 0, end: Int = this.size): Int {
        for (n in (end - 1) downTo start) if (data[n] == value) return n
        return -1
    }

    fun insertAt(index: Int, value: TGen) = this.apply {
        ensure(1)
        if (isNotEmpty()) arraycopy(data, index, data, index + 1, length - index)
        data[index] = value
        length++
    }

    fun insertAt(index: Int, value: Array<TGen>, start: Int = 0, end: Int = value.size) = this.apply {
        val count = end - start
        ensure(count)
        if (isNotEmpty()) arraycopy(data, index, data, index + count, length - index)
        for (n in 0 until count) data[index + n] = value[start + n]
        length += count
    }

    fun swapIndices(indexA: Int, indexB: Int) {
        val l = this.getAt(indexA)
        val r = this.getAt(indexB)
        this[indexA] = r
        this[indexB] = l
    }

    fun removeAt(index: Int): TGen = removeAt(index, 1)

    fun removeAt(index: Int, count: Int): TGen {
        if (index < 0 || index >= length || index + count > length) throw IndexOutOfBoundsException()
        val out = data[index]
        if (count > 0) {
            if (index < length - count) arraycopy(data, index + count, data, index, length - index - count)
            length-= count
        }
        return out
    }

    fun toTGenArray() = this.data.copyOf(length)

    // List interface

    override fun indexOf(element: TGen): Int = indexOf(element, 0, size)
    override fun lastIndexOf(element: TGen): Int = lastIndexOf(element, 0, size)

    override fun listIterator(): ListIterator<TGen> = listIterator(0)
    override fun listIterator(index: Int): ListIterator<TGen> = data.take(length).listIterator(index)
    override fun subList(fromIndex: Int, toIndex: Int): List<TGen> = data.asList().subList(fromIndex, toIndex)

    // Data
    override fun hashCode(): Int = data.contentHashCode()
    override fun equals(other: Any?): Boolean {
        if (other is TGenArrayList<*/*_TGen_*/>) return data.contentEquals(other.data)
        if (other is List<*>) return other == this
        return false
    }

    override fun toString(): String = StringBuilder(2 + 5 * size).also { sb ->
        sb.append('[')
        for (n in 0 until size) {
            if (n != 0) sb.append(", ")
            sb.append(this.getAt(n))
        }
        sb.append(']')
    }.toString()
}

fun <TGen> tgenArrayListOf(vararg values: TGen) = TGenArrayList<TGen>(*values)
