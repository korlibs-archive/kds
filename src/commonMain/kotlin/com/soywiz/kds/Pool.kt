package com.soywiz.kds

class Pool<T>(private val reset: (T) -> Unit = {}, preallocate: Int = 0, private val gen: (Int) -> T) {
    constructor(preallocate: Int = 0, gen: (Int) -> T) : this({}, preallocate, gen)

    private val items = Stack<T>()
    private var lastId = 0

    val itemsInPool: Int get() = items.size

    init {
        for (n in 0 until preallocate) items.push(gen(lastId++))
    }

    fun alloc(): T = if (items.isNotEmpty()) items.pop() else gen(lastId++)

    fun free(element: T) {
        reset(element)
        items.push(element)
    }

    fun free(vararg elements: T) = run { for (element in elements) free(element) }

    fun free(elements: Iterable<T>) = run { for (element in elements) free(element) }

    inline fun <T2> alloc(crossinline callback: (T) -> T2): T2 {
        val temp = alloc()
        try {
            return callback(temp)
        } finally {
            free(temp)
        }
    }
}
