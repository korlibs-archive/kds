package com.soywiz.kds

class Stack<T>() {
    private val items = arrayListOf<T>()

    val size: Int get() = items.size
    val hasMore: Boolean get() = size > 0
    fun isEmpty() = size == 0
    fun isNotEmpty() = size != 0

    constructor(vararg items: T) : this() {
        for (item in items) push(item)
    }

    fun push(v: T) {
        items.add(v)
    }

    fun pop(): T = items.removeAt(items.size - 1)
}

class IntStack() {
    private val items = intArrayListOf()

    val size: Int get() = items.size
    val hasMore: Boolean get() = size > 0
    fun isEmpty() = size == 0
    fun isNotEmpty() = size != 0

    constructor(vararg items: Int) : this() {
        for (item in items) push(item)
    }

    fun push(v: Int) {
        items.add(v)
    }

    fun pop(): Int = items.removeAt(items.size - 1)
}

class DoubleStack() {
    private val items = doubleArrayListOf()

    val size: Int get() = items.size
    val hasMore: Boolean get() = size > 0
    fun isEmpty() = size == 0
    fun isNotEmpty() = size != 0

    constructor(vararg items: Double) : this() {
        for (item in items) push(item)
    }

    fun push(v: Double) {
        items.add(v)
    }

    fun pop(): Double = items.removeAt(items.size - 1)
}
