package com.soywiz.kds

import com.soywiz.kds.internal.*

inline fun <reified T> mapWhile(cond: () -> Boolean, gen: (Int) -> T): Array<T> =
	arrayListOf<T>().apply { while (cond()) this += gen(this.size) }.toTypedArray()

fun mapWhileInt(cond: () -> Boolean, gen: (Int) -> Int): IntArray =
	IntArrayList().apply { this += gen(this.size) }.toIntArray()

fun mapWhileFloat(cond: () -> Boolean, gen: (Int) -> Float): FloatArray =
	FloatArrayList().apply { while (cond()) this += gen(this.size) }.toFloatArray()

fun mapWhileDouble(cond: () -> Boolean, gen: (Int) -> Double): DoubleArray =
	DoubleArrayList().apply { while (cond()) this += gen(this.size) }.toDoubleArray()

fun <T> List<T>.getCyclic(index: Int) = this[index umod this.size]
fun <T> Array<T>.getCyclic(index: Int) = this[index umod this.size]

@Deprecated("")
fun <T> MutableList<T>.splice(removeOffset: Int, removeCount: Int, vararg itemsToAdd: T) {
	// @TODO: Improve performance
	for (n in 0 until removeCount) this.removeAt(removeOffset)
	for (n in 0 until itemsToAdd.size) {
		this.add(removeOffset + n, itemsToAdd[n])
	}
}

@Deprecated("")
fun <T, R> Iterable<T>.reduceAcumulate(init: R, reductor: (R, T) -> R): R {
	var acc = init
	for (item in this) acc = reductor(acc, item)
	return acc
}

fun <K, V> linkedHashMapOf(vararg pairs: Pair<K, V>): LinkedHashMap<K, V> =
	LinkedHashMap<K, V>().also { for ((key, value) in pairs) it[key] = value }

fun <K, V> Iterable<Pair<K, V>>.toLinkedMap(): LinkedHashMap<K, V> =
	LinkedHashMap<K, V>().also { for ((key, value) in this) it[key] = value }

fun <K, V> Map<K, V>.flip(): Map<V, K> = this.map { Pair(it.value, it.key) }.toMap()

fun <T> List<T>.countMap(): Map<T, Int> {
	val counts = hashMapOf<T, Int>()
	for (key in this) {
		if (key !in counts) counts[key] = 0
		counts[key] = counts[key]!! + 1
	}
	return counts
}

fun <K> MutableMap<K, Int>.incr(key: K, delta: Int = +1): Int {
	val next = this.getOrPut(key) { 0 } + delta
	this[key] = next
	return next
}
fun <T> Map<String, T>.toCaseInsensitiveTreeMap(): Map<String, T> {
	val res = CaseInsensitiveHashMap<T>()
	res.putAll(this)
	return res
}

class CaseInsensitiveHashMap<T>(
	private val mapOrig: MutableMap<String, T> = LinkedHashMap(),
	private val lcToOrig: MutableMap<String, String> = LinkedHashMap(),
	private val mapLC: MutableMap<String, T> = LinkedHashMap()
) : MutableMap<String, T> by mapOrig {
	override fun containsKey(key: String): Boolean = mapLC.containsKey(key.toLowerCase())

	override fun clear() {
		mapOrig.clear()
		mapLC.clear()
		lcToOrig.clear()
	}

	override fun get(key: String): T? = mapLC[key.toLowerCase()]

	override fun put(key: String, value: T): T? {
		remove(key)
		mapOrig.put(key, value)
		lcToOrig.put(key.toLowerCase(), key)
		return mapLC.put(key.toLowerCase(), value)
	}

	override fun putAll(from: Map<out String, T>) {
		for (v in from) put(v.key, v.value)
	}

	override fun remove(key: String): T? {
		val lkey = key.toLowerCase()
		val okey = lcToOrig[lkey]
		mapOrig.remove(okey)
		val res = mapLC.remove(lkey)
		lcToOrig.remove(lkey)
		return res
	}
}

/**
 * Returns the index of an item or a negative number in the case the item is not found.
 * The negative index represents the nearest position after negating + 1.
 */
fun IntArray.binarySearch(v: Int, fromIndex: Int = 0, toIndex: Int = size): BSearchResult =
	BSearchResult(binarySearch(fromIndex, toIndex) { this[it].compareTo(v) })

fun DoubleArray.binarySearch(v: Double, fromIndex: Int = 0, toIndex: Int = size): BSearchResult =
	BSearchResult(binarySearch(fromIndex, toIndex) { this[it].compareTo(v) })

fun IntArrayList.binarySearch(v: Int, fromIndex: Int = 0, toIndex: Int = size): BSearchResult =
	BSearchResult(binarySearch(fromIndex, toIndex) { this[it].compareTo(v) })

fun DoubleArrayList.binarySearch(v: Double, fromIndex: Int = 0, toIndex: Int = size): BSearchResult =
	BSearchResult(binarySearch(fromIndex, toIndex) { this[it].compareTo(v) })

inline fun binarySearch(fromIndex: Int, toIndex: Int, invalid: (from: Int, to: Int, low: Int, high: Int) -> Int = { from, to, low, high -> -low - 1 }, check: (value: Int) -> Int): Int {
	var low = fromIndex
	var high = toIndex - 1

	while (low <= high) {
		val mid = (low + high) / 2
		val mval = check(mid)

		when {
			mval < 0 -> low = mid + 1
			mval > 0 -> high = mid - 1
			else -> return mid
		}
	}
	return invalid(fromIndex, toIndex, low, high)
}

inline class BSearchResult(val raw: Int) {
	val found: Boolean get() = raw >= 0
	val index: Int get() = if (found) raw else -1
	val nearIndex: Int get() = if (found) raw else -raw - 1
}
