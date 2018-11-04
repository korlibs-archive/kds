package com.soywiz.kds

import com.soywiz.kds.internal.*

// GENERIC

class GenericArrayList<T>(capacity: Int = 7) : Collection<T> {
    var data: Array<T> = arrayOfNulls<Any>(capacity) as Array<T>; private set
    internal val capacity: Int get() = data.size
    private var length: Int = 0
    override var size: Int get() = length
        set(value) {
            ensure(value)
            this.length = value
        }

    constructor(other: GenericArrayList<T>) : this() {
        add(other)
    }

    constructor(vararg other: T) : this() {
        add(other)
    }

    private fun ensure(count: Int) {
        if (length + count > data.size) {
            data = data.copyOf(kotlin.math.max(length + count, data.size * 3)) as Array<T>
        }
    }

    fun clear() = run { length = 0 }

    fun add(value: T) {
        ensure(1)
        data[length++] = value
    }

    operator fun plusAssign(value: T) = add(value)
    operator fun plusAssign(value: Array<T>) = add(value)
    operator fun plusAssign(value: GenericArrayList<T>) = add(value)
    operator fun plusAssign(value: Iterable<T>) = add(value)

    fun add(values: Array<out T>, offset: Int = 0, length: Int = values.size) {
        ensure(values.size)
        MemTools.arraycopy(values as Array<T>, offset, data, this.size, length)
        this.size += values.size
    }

    fun add(values: GenericArrayList<T>) = add(values.data, 0, values.size)
    fun add(values: Iterable<T>) = run { for (v in values) add(v) }

    operator fun get(index: Int): T {
        //return if (index >= 0 && index < length) data[index] else 0f
        return data[index]
    }
    operator fun set(index: Int, value: T) = run {
        if (index >= length) {
            ensure(index + 1)
            length = index + 1
        }
        data[index] = value
    }

    override fun iterator(): Iterator<T> = data.take(length).iterator()

    override fun contains(element: T): Boolean {
        for (n in 0 until length) if (this.data[n] == element) return true
        return false
    }

    override fun containsAll(elements: Collection<T>): Boolean {
        for (e in elements) if (!contains(e)) return false
        return true
    }

    @Suppress("ReplaceSizeZeroCheckWithIsEmpty")
    override fun isEmpty(): Boolean = this.size == 0

    fun indexOf(value: T, start: Int = 0, end: Int = this.size): Int {
        for (n in start until end) if (data[n] == value) return n
        return -1
    }

    fun removeAt(index: Int): T {
        if (index < 0 || index >= length) throw IndexOutOfBoundsException()
        val out = data[index]
        if (index < length - 1) MemTools.arraycopy(data, index + 1, data, index, length - index - 1)
        length--
        return out
    }

    fun toGenericArray() = this.data.copyOf(length)
}

fun <T> genericArrayListOf(vararg values: T) = GenericArrayList<T>(*values)

// SPECIFIC - Do not modify from here

class IntArrayList(capacity: Int = 7) : IntCollection {
    var data: IntArray = IntArray(capacity) as IntArray; private set
    internal val capacity: Int get() = data.size
    private var length: Int = 0
    override var size: Int get() = length
        set(value) {
            ensure(value)
            this.length = value
        }

    constructor(other: IntArrayList) : this() {
        add(other)
    }

    constructor(vararg other: Int) : this() {
        add(other)
    }

    private fun ensure(count: Int) {
        if (length + count > data.size) {
            data = data.copyOf(kotlin.math.max(length + count, data.size * 3)) as IntArray
        }
    }

    fun clear() = run { length = 0 }

    fun add(value: Int) {
        ensure(1)
        data[length++] = value
    }

    operator fun plusAssign(value: Int) = add(value)
    operator fun plusAssign(value: IntArray) = add(value)
    operator fun plusAssign(value: IntArrayList) = add(value)
    operator fun plusAssign(value: IntIterable) = add(value)

    fun add(values: IntArray, offset: Int = 0, length: Int = values.size) {
        ensure(values.size)
        MemTools.arraycopy(values as IntArray, offset, data, this.size, length)
        this.size += values.size
    }

    fun add(values: IntArrayList) = add(values.data, 0, values.size)
    fun add(values: IntIterable) = run { for (v in values) add(v) }

    operator fun get(index: Int): Int {
        //return if (index >= 0 && index < length) data[index] else 0f
        return data[index]
    }
    operator fun set(index: Int, value: Int) = run {
        if (index >= length) {
            ensure(index + 1)
            length = index + 1
        }
        data[index] = value
    }

    override fun iterator(): Iterator<Int> = data.take(length).iterator()

    override fun contains(element: Int): Boolean {
        for (n in 0 until length) if (this.data[n] == element) return true
        return false
    }

    override fun containsAll(elements: IntCollection): Boolean {
        for (e in elements) if (!contains(e)) return false
        return true
    }

    @Suppress("ReplaceSizeZeroCheckWithIsEmpty")
    override fun isEmpty(): Boolean = this.size == 0

    fun indexOf(value: Int, start: Int = 0, end: Int = this.size): Int {
        for (n in start until end) if (data[n] == value) return n
        return -1
    }

    fun removeAt(index: Int): Int {
        if (index < 0 || index >= length) throw IndexOutOfBoundsException()
        val out = data[index]
        if (index < length - 1) MemTools.arraycopy(data, index + 1, data, index, length - index - 1)
        length--
        return out
    }

    fun toIntArray() = this.data.copyOf(length)
}

fun intArrayListOf(vararg values: Int) = IntArrayList(*values)

class FloatArrayList(capacity: Int = 7) : FloatCollection {
    var data: FloatArray = FloatArray(capacity) as FloatArray; private set
    internal val capacity: Int get() = data.size
    private var length: Int = 0
    override var size: Int get() = length
        set(value) {
            ensure(value)
            this.length = value
        }

    constructor(other: FloatArrayList) : this() {
        add(other)
    }

    constructor(vararg other: Float) : this() {
        add(other)
    }

    private fun ensure(count: Int) {
        if (length + count > data.size) {
            data = data.copyOf(kotlin.math.max(length + count, data.size * 3)) as FloatArray
        }
    }

    fun clear() = run { length = 0 }

    fun add(value: Float) {
        ensure(1)
        data[length++] = value
    }

    operator fun plusAssign(value: Float) = add(value)
    operator fun plusAssign(value: FloatArray) = add(value)
    operator fun plusAssign(value: FloatArrayList) = add(value)
    operator fun plusAssign(value: FloatIterable) = add(value)

    fun add(values: FloatArray, offset: Int = 0, length: Int = values.size) {
        ensure(values.size)
        MemTools.arraycopy(values as FloatArray, offset, data, this.size, length)
        this.size += values.size
    }

    fun add(values: FloatArrayList) = add(values.data, 0, values.size)
    fun add(values: FloatIterable) = run { for (v in values) add(v) }

    operator fun get(index: Int): Float {
        //return if (index >= 0 && index < length) data[index] else 0f
        return data[index]
    }
    operator fun set(index: Int, value: Float) = run {
        if (index >= length) {
            ensure(index + 1)
            length = index + 1
        }
        data[index] = value
    }

    override fun iterator(): Iterator<Float> = data.take(length).iterator()

    override fun contains(element: Float): Boolean {
        for (n in 0 until length) if (this.data[n] == element) return true
        return false
    }

    override fun containsAll(elements: FloatCollection): Boolean {
        for (e in elements) if (!contains(e)) return false
        return true
    }

    @Suppress("ReplaceSizeZeroCheckWithIsEmpty")
    override fun isEmpty(): Boolean = this.size == 0

    fun indexOf(value: Float, start: Int = 0, end: Int = this.size): Int {
        for (n in start until end) if (data[n] == value) return n
        return -1
    }

    fun removeAt(index: Int): Float {
        if (index < 0 || index >= length) throw IndexOutOfBoundsException()
        val out = data[index]
        if (index < length - 1) MemTools.arraycopy(data, index + 1, data, index, length - index - 1)
        length--
        return out
    }

    fun toFloatArray() = this.data.copyOf(length)
}

fun floatArrayListOf(vararg values: Float) = FloatArrayList(*values)

class DoubleArrayList(capacity: Int = 7) : DoubleCollection {
    var data: DoubleArray = DoubleArray(capacity) as DoubleArray; private set
    internal val capacity: Int get() = data.size
    private var length: Int = 0
    override var size: Int get() = length
        set(value) {
            ensure(value)
            this.length = value
        }

    constructor(other: DoubleArrayList) : this() {
        add(other)
    }

    constructor(vararg other: Double) : this() {
        add(other)
    }

    private fun ensure(count: Int) {
        if (length + count > data.size) {
            data = data.copyOf(kotlin.math.max(length + count, data.size * 3)) as DoubleArray
        }
    }

    fun clear() = run { length = 0 }

    fun add(value: Double) {
        ensure(1)
        data[length++] = value
    }

    operator fun plusAssign(value: Double) = add(value)
    operator fun plusAssign(value: DoubleArray) = add(value)
    operator fun plusAssign(value: DoubleArrayList) = add(value)
    operator fun plusAssign(value: DoubleIterable) = add(value)

    fun add(values: DoubleArray, offset: Int = 0, length: Int = values.size) {
        ensure(values.size)
        MemTools.arraycopy(values as DoubleArray, offset, data, this.size, length)
        this.size += values.size
    }

    fun add(values: DoubleArrayList) = add(values.data, 0, values.size)
    fun add(values: DoubleIterable) = run { for (v in values) add(v) }

    operator fun get(index: Int): Double {
        //return if (index >= 0 && index < length) data[index] else 0f
        return data[index]
    }
    operator fun set(index: Int, value: Double) = run {
        if (index >= length) {
            ensure(index + 1)
            length = index + 1
        }
        data[index] = value
    }

    override fun iterator(): Iterator<Double> = data.take(length).iterator()

    override fun contains(element: Double): Boolean {
        for (n in 0 until length) if (this.data[n] == element) return true
        return false
    }

    override fun containsAll(elements: DoubleCollection): Boolean {
        for (e in elements) if (!contains(e)) return false
        return true
    }

    @Suppress("ReplaceSizeZeroCheckWithIsEmpty")
    override fun isEmpty(): Boolean = this.size == 0

    fun indexOf(value: Double, start: Int = 0, end: Int = this.size): Int {
        for (n in start until end) if (data[n] == value) return n
        return -1
    }

    fun removeAt(index: Int): Double {
        if (index < 0 || index >= length) throw IndexOutOfBoundsException()
        val out = data[index]
        if (index < length - 1) MemTools.arraycopy(data, index + 1, data, index, length - index - 1)
        length--
        return out
    }

    fun toDoubleArray() = this.data.copyOf(length)
}

fun doubleArrayListOf(vararg values: Double) = DoubleArrayList(*values)
