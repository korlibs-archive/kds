package com.soywiz.kds

// GENERIC

@Suppress("UNCHECKED_CAST", "RemoveExplicitTypeArguments")
class PriorityQueue<TGen>
@PublishedApi internal constructor(private var data: Array<TGen>, val comparator: Comparator<TGen>) : MutableCollection<TGen> {
    companion object {
        operator fun <TGen> invoke(comparator: Comparator<TGen>, reversed: Boolean = false): PriorityQueue<TGen> =
            PriorityQueue<TGen>(arrayOfNulls<Any>(16) as Array<TGen>, if (reversed) comparator.reversed() else comparator)

        operator fun <TGen> invoke(reversed: Boolean = false, comparator: (left: TGen, right: TGen) -> Int): PriorityQueue<TGen> =
            PriorityQueue<TGen>(Comparator(comparator), reversed)

        operator fun <TGen : Comparable<TGen>> invoke(reversed: Boolean = false): PriorityQueue<TGen> =
            PriorityQueue<TGen>(comparator(), reversed)
    }

    private var Int.value
        get() = data[this]
        set(value) = run { data[this] = value }
    private val Int.isRoot get() = this == 0
    private val Int.parent: Int get() = (this - 1) / 2
    private val Int.left: Int get() = 2 * this + 1
    private val Int.right: Int get() = 2 * this + 2

    private fun gt(a: TGen, b: TGen) = comparator.compare(a, b) > 0
    private fun lt(a: TGen, b: TGen) = comparator.compare(a, b) < 0

    private val capacity get() = data.size
    override var size = 0; private set
    val head: TGen get() {
        if (size <= 0) throw IndexOutOfBoundsException()
        return data[0]
    }

    override fun add(element: TGen): Boolean {
        size++
        ensure(size)
        var i = (size - 1)
        i.value = element
        while (!i.isRoot && gt(i.parent.value, i.value)) {
            swap(i, i.parent)
            i = i.parent
        }
        return true
    }

    fun removeHead(): TGen {
        if (size <= 0) throw IndexOutOfBoundsException()
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
            data = data.copyOf(2 + capacity * 2) as Array<TGen>
        }
    }

    private fun minHeapify(index: Int) {
        var i = index
        while (true) {
            val left = i.left
            val right = i.right
            var smallest = i
            if (left < size && lt(left.value, i.value)) smallest = left
            if (right < size && lt(right.value, smallest.value)) smallest = right
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

    override operator fun contains(element: TGen): Boolean = (0 until size).any { it.value == element }

    override fun containsAll(elements: Collection<TGen>): Boolean {
        val thisSet = this.toSet()
        return elements.all { it in thisSet }
    }

    override fun isEmpty(): Boolean = size == 0
    override fun addAll(elements: Collection<TGen>): Boolean = run { for (e in elements) add(e); elements.isNotEmpty() }
    override fun clear() = run { size = 0 }

    override fun remove(element: TGen): Boolean {
        val temp = ArrayList(toList())
        val res = temp.remove(element)
        clear()
        addAll(temp)
        return res
    }

    override fun removeAll(elements: Collection<TGen>): Boolean {
        val temp = ArrayList(toList())
        val res = temp.removeAll(elements)
        clear()
        addAll(temp)
        return res
    }

    override fun retainAll(elements: Collection<TGen>): Boolean {
        val temp = ArrayList(toList())
        val res = temp.retainAll(elements)
        clear()
        addAll(temp)
        return res
    }

    override fun iterator(): MutableIterator<TGen> {
        var index = 0
        return object : MutableIterator<TGen> {
            override fun hasNext(): Boolean = index < size
            override fun next(): TGen = (index++).value
            override fun remove() = TODO()
        }
    }
}
