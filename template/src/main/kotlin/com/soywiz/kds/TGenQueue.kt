package com.soywiz.kds

// GENERIC

class Queue<TGen>() {
    private val items = CircularList<TGen>()

    val size: Int get() = items.size
    val hasMore: Boolean get() = size > 0
    fun isEmpty() = size == 0
    fun isNotEmpty() = size != 0

    constructor(vararg items: TGen) : this() {
        for (item in items) enqueue(item)
    }

    fun enqueue(v: TGen) = run { items.addLast(v) }
    fun dequeue(): TGen = items.removeFirst()
    fun remove(v: TGen) = run { items.remove(v) }
    fun toList() = items.toList()
}
