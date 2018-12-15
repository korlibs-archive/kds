package com.soywiz.kds


@Suppress("UNCHECKED_CAST")
class PriorityQueue<T>
@PublishedApi internal constructor(private var data: Array<T>, val comparator: Comparator<T>) : MutableCollection<T> {
    companion object {
        inline operator fun <reified T> invoke(comparator: Comparator<T>, reversed: Boolean = false) =
            PriorityQueue(arrayOfNulls<T>(16) as Array<T>, if (reversed) comparator.reversed() else comparator)

        inline operator fun <reified T : Comparable<T>> invoke(reversed: Boolean = false) =
            PriorityQueue<T>(comparator(), reversed)
    }

    private var Int.value
        get() = data[this]
        set(value) = run { data[this] = value }
    private val Int.isRoot get() = this == 0
    private val Int.parent: Int get() = (this - 1) / 2
    private val Int.left: Int get() = 2 * this + 1
    private val Int.right: Int get() = 2 * this + 2
    private operator fun T.compareTo(other: T): Int = comparator.compare(this, other)

    private val capacity get() = data.size
    override var size = 0; private set
    val head: T? get() = data.getOrNull(0)

    override fun add(element: T): Boolean {
        size++
        ensure(size)
        var i = (size - 1)
        i.value = element
        while (!i.isRoot && i.parent.value > i.value) {
            swap(i, i.parent)
            i = i.parent
        }
        return true
    }

    fun removeHead(): T? {
        if (size <= 0) return null
        if (size == 1) {
            size--
            return 0.value
        }
        val root = 0.value
        0.value = (size - 1).value
        size--
        minHeapify(0)
        return root
    }

    private fun ensure(index: Int) {
        if (index >= capacity) {
            data = data.copyOf(2 + capacity * 2) as Array<T>
        }
    }

    private fun minHeapify(index: Int) {
        var i = index
        while (true) {
            val left = i.left
            val right = i.right
            var smallest = i
            if (left < size && left.value < i.value) smallest = left
            if (right < size && right.value < smallest.value) smallest = right
            if (smallest != i) {
                swap(i, smallest)
                i = smallest
            } else {
                break
            }
        }
    }

    private fun swap(l: Int, r: Int) {
        val temp = r.value
        r.value = l.value
        l.value = temp
    }

    override operator fun contains(element: T): Boolean = (0 until size).any { it.value == element }

    override fun containsAll(elements: Collection<T>): Boolean {
        val thisSet = this.toSet()
        return elements.all { it in thisSet }
    }

    override fun isEmpty(): Boolean = size == 0
    override fun addAll(elements: Collection<T>): Boolean = run { for (e in elements) add(e); elements.isNotEmpty() }
    override fun clear() = run { size = 0 }

    override fun remove(element: T): Boolean {
        val temp = ArrayList(toList())
        val res = temp.remove(element)
        clear()
        addAll(temp)
        return res
    }

    override fun removeAll(elements: Collection<T>): Boolean {
        val temp = ArrayList(toList())
        val res = temp.removeAll(elements)
        clear()
        addAll(temp)
        return res
    }

    override fun retainAll(elements: Collection<T>): Boolean {
        val temp = ArrayList(toList())
        val res = temp.retainAll(elements)
        clear()
        addAll(temp)
        return res
    }

    override fun iterator(): MutableIterator<T> {
        var index = 0
        return object : MutableIterator<T> {
            override fun hasNext(): Boolean = index < size
            override fun next(): T = (index++).value
            override fun remove() = TODO()
        }
    }
}
