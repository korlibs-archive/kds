package com.soywiz.kds

class Queue<T>() {
	private val items = CircularList<T>()

	val size: Int get() = items.size
	val hasMore: Boolean get() = size > 0
	fun isEmpty() = size == 0
	fun isNotEmpty() = size != 0

	constructor(vararg items: T) : this() {
		for (item in items) enqueue(item)
	}

	fun enqueue(v: T) = run { items.addLast(v) }
	fun dequeue(): T = items.removeFirst()
	fun remove(v: T) = run { items.remove(v) }
	fun toList() = items.toList()
}

class IntQueue() {
	private val items = IntCircularList()

	val size: Int get() = items.size
	val hasMore: Boolean get() = size > 0
	fun isEmpty() = size == 0
	fun isNotEmpty() = size != 0

	constructor(vararg items: Int) : this() {
		for (item in items) enqueue(item)
	}

	fun enqueue(v: Int) = run { items.addLast(v) }
	fun dequeue(): Int = items.removeFirst()
	fun remove(v: Int) = run { items.remove(v) }
	fun toList() = items.toList()
}

class DoubleQueue() {
	private val items = DoubleCircularList()

	val size: Int get() = items.size
	val hasMore: Boolean get() = size > 0
	fun isEmpty() = size == 0
	fun isNotEmpty() = size != 0

	constructor(vararg items: Double) : this() {
		for (item in items) enqueue(item)
	}

	fun enqueue(v: Double) = run { items.addLast(v) }
	fun dequeue(): Double = items.removeFirst()
	fun remove(v: Double) = run { items.remove(v) }
	fun toList() = items.toList()
}
