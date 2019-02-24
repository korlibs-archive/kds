package com.soywiz.kds.iterators

import kotlin.test.*

class FastIteratorsTest {
	@Test
	fun testFastIterateRemove() {
		assertEquals(listOf(1, 3, 5, 5, 3), arrayListOf(1, 2, 3, 4, 5, 5, 8, 8, 3).fastIterateRemove { it % 2 == 0 })
	}
}