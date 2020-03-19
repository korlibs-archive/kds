package com.soywiz.kds

import com.soywiz.kds.internal.*

typealias Deque<TGen> = TGenDeque<TGen>

// GENERIC

typealias CircularList<TGen> = TGenDeque<TGen>

/**
 * Deque structure supporting constant time of appending/removing from the start or the end of the list
 * when there is room in the underlying array.
 */
open class TGenDeque<TGen>(initialCapacity: Int) : MutableCollection<TGen> {
    private var _start: Int = 0
    private var _size: Int = 0
    private var data: Array<TGen> = arrayOfNulls<Any>(initialCapacity) as Array<TGen>
    private val capacity: Int get() = data.size

    constructor() : this(initialCapacity = 16)

    override val size: Int get() = _size

    override fun isEmpty(): Boolean = size == 0

    private fun resizeIfRequiredFor(count: Int) {
        if (size + count > capacity) {
            val i = this.data
            val istart = this._start
            val o = arrayOfNulls<Any>(this.data.size * 2) as Array<TGen>
            copyCyclic(i, istart, o, this._size)
            this.data = o
            this._start = 0
        }
    }

    private fun copyCyclic(i: Array<TGen>, istart: Int, o: Array<TGen>, count: Int) {
        val size1 = kotlin.math.min(i.size - istart, count)
        val size2 = count - size1
        arraycopy(i, istart, o, 0, size1)
        if (size2 > 0) arraycopy(i, 0, o, size1, size2)
    }

    fun addAll(items: Iterable<TGen>) = run {
        resizeIfRequiredFor(items.count())
        for (i in items) addLast(i)
    }

    fun addAllFirst(items: List<TGen>) {
        resizeIfRequiredFor(items.size)
        _start = (_start - items.size) umod capacity
        _size += items.size
        for (n in items.indices) {
            data[(_start + n) umod capacity] = items[n]
        }
    }

    fun addFirst(item: TGen) {
        resizeIfRequiredFor(1)
        _start = (_start - 1) umod capacity
        _size++
        data[_start] = item
    }

    fun addLast(item: TGen) {
        resizeIfRequiredFor(1)
        data[(_start + size) umod capacity] = item
        _size++
    }

    fun removeFirst(): TGen {
        if (_size <= 0) throw IndexOutOfBoundsException()
        return first.apply { _start = (_start + 1) umod capacity; _size-- }
    }

    fun removeLast(): TGen {
        if (_size <= 0) throw IndexOutOfBoundsException()
        return last.apply { _size-- }
    }

    fun removeAt(index: Int): TGen {
        if (index < 0 || index >= size) throw IndexOutOfBoundsException()
        if (index == 0) return removeFirst()
        if (index == size - 1) return removeLast()

        // @TODO: We could use two arraycopy per branch to prevent umodding twice per element.
        val old = this[index]
        if (index < size / 2) {
            for (n in index downTo 1) this[n] = this[n - 1]
            _start = (_start + 1) umod capacity
        } else {
            for (n in index until size - 1) this[n] = this[n + 1]
        }

        _size--
        return old
    }

    override fun add(element: TGen): Boolean = true.apply { addLast(element) }
    override fun addAll(elements: Collection<TGen>): Boolean = true.apply { addAll(elements as Iterable<TGen>) }
    override fun clear() = run { _size = 0 }
    override fun remove(element: TGen): Boolean {
        val index = indexOf(element)
        if (index >= 0) removeAt(index)
        return (index >= 0)
    }

    override fun removeAll(elements: Collection<TGen>): Boolean = _removeRetainAll(elements, retain = false)
    override fun retainAll(elements: Collection<TGen>): Boolean = _removeRetainAll(elements, retain = true)

    private fun _removeRetainAll(elements: Collection<TGen>, retain: Boolean): Boolean {
        val eset = elements.toSet()
        val temp = this.data.copyOf()
        var tsize = 0
        val osize = size
        for (n in 0 until size) {
            val c = this[n]
            if ((c in eset) == retain) {
                temp[tsize++] = c
            }
        }
        this.data = temp
        this._start = 0
        this._size = tsize
        return tsize != osize
    }

    val first: TGen get() = data[_start]
    val last: TGen get() = data[internalIndex(size - 1)]

    private fun internalIndex(index: Int) = (_start + index) umod capacity

    operator fun set(index: Int, value: TGen): Unit = run { data[internalIndex(index)] = value }
    operator fun get(index: Int): TGen = data[internalIndex(index)]

    override fun contains(element: TGen): Boolean = (0 until size).any { this[it] == element }

    fun indexOf(element: TGen): Int {
        for (n in 0 until size) if (this[n] == element) return n
        return -1
    }

    override fun containsAll(elements: Collection<TGen>): Boolean {
        val emap = elements.map { it to 0 }.toLinkedMap()
        for (it in 0 until size) {
            val e = this[it]
            if (e in emap) emap[e] = 1
        }
        return emap.values.all { it == 1 }
    }

    override fun iterator(): MutableIterator<TGen> {
        val that = this
        return object : MutableIterator<TGen> {
            var index = 0
            override fun next(): TGen = that[index++]
            override fun hasNext(): Boolean = index < size
            override fun remove(): Unit = run { removeAt(--index) }
        }
    }

    override fun hashCode(): Int = contentHashCode(size) { this[it] }

    override fun equals(other: Any?): Boolean {
        if (other !is TGenDeque<*/*_TGen_*/>) return false
        if (other.size != this.size) return false
        for (n in 0 until size) if (this[n] != other[n]) return false
        return true
    }

    override fun toString(): String {
        val sb = StringBuilder()
        sb.append('[')
        for (n in 0 until size) {
            sb.append(this[n])
            if (n != size - 1) sb.append(", ")
        }
        sb.append(']')
        return sb.toString()
    }
}
