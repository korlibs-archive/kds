package com.soywiz.kds

class IndexedTable<T> : Iterable<T> {
	val instances = arrayListOf<T>()
	val size get() = instances.size
	private val instanceToIndex = LinkedHashMap<T, Int>()

	fun add(str: T) {
		get(str)
	}

	operator fun get(str: T): Int {
		return instanceToIndex.getOrPut(str) { instances.size.also { instances += str } }
	}

	override fun iterator(): Iterator<T> = instances.iterator()
}
