package com.soywiz.kds

typealias Queue<TGen> = TGenQueue<TGen>

// GENERIC

/**
 * A FIFO (First In First Out) structure.
 */
class TGenQueue<TGen>() : Collection<TGen> {
    private val items = TGenDeque<TGen>()

    override val size: Int get() = items.size
    override fun isEmpty() = size == 0

    constructor(vararg items: TGen) : this() {
        for (item in items) enqueue(item)
    }

    fun enqueue(v: TGen) = run { items.addLast(v) }
    fun peek(): TGen? = items.firstOrNull()
    fun dequeue(): TGen = items.removeFirst()
    fun remove(v: TGen) = run { items.remove(v) }
    fun toList() = items.toList()
    fun clear() = items.clear()

    override fun contains(element: TGen): Boolean = items.contains(element)
    override fun containsAll(elements: Collection<TGen>): Boolean = items.containsAll(elements)
    override fun iterator(): Iterator<TGen> = items.iterator()

    override fun hashCode(): Int = items.hashCode()
    override fun equals(other: Any?): Boolean = (other is TGenQueue<*/*TGen*/>) && items == other.items
}
