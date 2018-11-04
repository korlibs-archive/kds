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
