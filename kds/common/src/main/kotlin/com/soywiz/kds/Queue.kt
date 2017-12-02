package com.soywiz.kds

class Queue<T>() {
	private val items = LinkedList<T>()

	val size: Int get() = items.size

	constructor(vararg items: T) : this() {
		for (item in items) queue(item)
	}

	fun queue(v: T) {
		items.addLast(v)
	}

	fun dequeue(): T = items.removeFirst()
}
