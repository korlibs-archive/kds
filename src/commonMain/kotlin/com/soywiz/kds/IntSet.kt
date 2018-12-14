package com.soywiz.kds

class IntSet {
	private val data = IntMap<Unit>()

	fun clear() = run { data.clear() }
	fun add(item: Int) = run { data[item] = Unit }
	fun addAll(vararg items: Int) = run { for (item in items) add(item) }
	fun addAll(items: Iterable<Int>) = run { for (item in items) add(item) }
	operator fun contains(item: Int) = item in data
	fun remove(item: Int) = run { data.remove(item) }

	operator fun plusAssign(value: Int) = run { add(value); Unit }
	operator fun minusAssign(value: Int) = run { remove(value); Unit }

	override fun toString(): String {
		return "[${data.keys.joinToString(", ")}]"
	}
}

fun intSetOf(vararg values: Int) = IntSet().apply { for (value in values) add(value) }
