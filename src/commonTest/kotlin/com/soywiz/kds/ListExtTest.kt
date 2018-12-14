package com.soywiz.kds

import kotlin.test.Test
import kotlin.test.assertEquals

class ListExtTest {
    @Test
    fun testGetCyclic() {
        val list = listOf(5, 10, 20, 30)
        val cyclicList = (0 until 4).map { list }.flatten()
        for (n in cyclicList.indices) {
            assertEquals(cyclicList[n], list.getCyclic(n))
        }

        val cyclicListNeg = (0 until 4).map { list.reversed() }.flatten()
        for (n in cyclicListNeg.indices) {
            assertEquals(cyclicListNeg[n], list.getCyclic(-n - 1))
        }

        assertEquals(5, list.getCyclic(0))
        assertEquals(10, list.getCyclic(1))
        assertEquals(20, list.getCyclic(2))
        assertEquals(30, list.getCyclic(3))

        assertEquals(30, list.getCyclic(-1))
        assertEquals(20, list.getCyclic(-2))
        assertEquals(10, list.getCyclic(-3))
        assertEquals(5, list.getCyclic(-4))
        assertEquals(30, list.getCyclic(-5))
    }
}