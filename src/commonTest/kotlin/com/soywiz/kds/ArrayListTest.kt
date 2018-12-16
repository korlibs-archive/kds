package com.soywiz.kds

import kotlin.test.*

class ArrayListTest {
	@Test
	fun testInt() {
		val values = IntArrayList(2)
		assertEquals(0, values.size)
		assertEquals(2, values.capacity)
		values.add(1)
		assertEquals(listOf(1), values.toList())
		assertEquals(1, values.size)
		assertEquals(2, values.capacity)
		values.add(2)
		assertEquals(listOf(1, 2), values.toList())
		assertEquals(2, values.size)
		assertEquals(2, values.capacity)
		values.add(3)
		assertEquals(listOf(1, 2, 3), values.toList())
		assertEquals(3, values.size)
		assertEquals(6, values.capacity)

		run {
			val v = IntArrayList()
			v.add(1)
			v.add(2)
			v.add(3)
			assertEquals(listOf(1, 2, 3), v.toList())
			v.removeAt(1)
			assertEquals(listOf(1, 3), v.toList())
			assertEquals(2, v.size)
			v.removeAt(1)
			assertEquals(listOf(1), v.toList())
			v.removeAt(0)
			assertEquals(listOf(), v.toList())
		}
	}

	@Test
	fun testFloat() {
		val values = FloatArrayList(2)
		assertEquals(0, values.size)
		assertEquals(2, values.capacity)
		values.add(1f)
		assertEquals(listOf(1f), values.toList())
		assertEquals(1, values.size)
		assertEquals(2, values.capacity)
		values.add(2f)
		assertEquals(listOf(1f, 2f), values.toList())
		assertEquals(2, values.size)
		assertEquals(2, values.capacity)
		values.add(3f)
		assertEquals(listOf(1f, 2f, 3f), values.toList())
		assertEquals(3, values.size)
		assertEquals(6, values.capacity)

		run {
			val v = FloatArrayList()
			v.add(1f)
			v.add(2f)
			v.add(3f)
			assertEquals(listOf(1f, 2f, 3f), v.toList())
			v.removeAt(1)
			assertEquals(listOf(1f, 3f), v.toList())
			assertEquals(2, v.size)
			v.removeAt(1)
			assertEquals(listOf(1f), v.toList())
			v.removeAt(0)
			assertEquals(listOf(), v.toList())
		}
	}

	@Test
	fun testDouble() {
		val values = DoubleArrayList(2)
		assertEquals(0, values.size)
		assertEquals(2, values.capacity)
		values.add(1.0)
		assertEquals(listOf(1.0), values.toList())
		assertEquals(1, values.size)
		assertEquals(2, values.capacity)
		values.add(2.0)
		assertEquals(listOf(1.0, 2.0), values.toList())
		assertEquals(2, values.size)
		assertEquals(2, values.capacity)
		values.add(3.0)
		assertEquals(listOf(1.0, 2.0, 3.0), values.toList())
		assertEquals(3, values.size)
		assertEquals(6, values.capacity)

		run {
			val v = DoubleArrayList()
			v.add(1.0)
			v.add(2.0)
			v.add(3.0)
			assertEquals(listOf(1.0, 2.0, 3.0), v.toList())
			v.removeAt(1)
			assertEquals(listOf(1.0, 3.0), v.toList())
			assertEquals(2, v.size)
			v.removeAt(1)
			assertEquals(listOf(1.0), v.toList())
			v.removeAt(0)
			assertEquals(listOf(), v.toList())
		}
	}

	@Test
	fun map() {
		assertEquals(intArrayListOf(0, 6, 12, 18, 24), (0 until 10).mapInt { it * 3 }.filter { it % 2 == 0 })
	}

	@Test
	fun list() {
		assertEquals(intArrayListOf(1, 2, 3), listOf(1, 2, 3))
		assertEquals(listOf(1, 2, 3), intArrayListOf(1, 2, 3))
	}
}
