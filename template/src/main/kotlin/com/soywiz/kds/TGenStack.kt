package com.soywiz.kds

// GENERIC

/**
 * A LIFO (Last In First Out) structure.
 */
class Stack<TGen>() : Collection<TGen> {
    private val items = arrayListOf<TGen>()

    override val size: Int get() = items.size
    override fun isEmpty() = size == 0

    constructor(vararg items: TGen) : this() {
        for (item in items) push(item)
    }

    fun push(v: TGen) = run { items.add(v) }
    fun pop(): TGen = items.removeAt(items.size - 1)

    override fun contains(element: TGen): Boolean = items.contains(element)
    override fun containsAll(elements: Collection<TGen>): Boolean = items.containsAll(elements)
    override fun iterator(): Iterator<TGen> = items.iterator()
}
