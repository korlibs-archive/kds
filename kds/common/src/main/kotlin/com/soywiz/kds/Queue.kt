package com.soywiz.kds

class Queue<T>() {
	private val items = LinkedList<T>()

	val size: Int get() = items.size
	val hasMore: Boolean get() = size > 0

	constructor(vararg items: T) : this() {
		for (item in items) queue(item)
	}

	fun queue(v: T) {
		items.addLast(v)
	}

	fun dequeue(): T = items.removeFirst()
}
