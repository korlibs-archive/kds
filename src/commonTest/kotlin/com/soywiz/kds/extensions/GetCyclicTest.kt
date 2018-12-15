package com.soywiz.kds.extensions

import com.soywiz.kds.*
import kotlin.test.Test
import kotlin.test.assertEquals

class GetCyclicTest {
    @Test
    fun list() {
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

    @Test
    fun array() {
        assertEquals("a", arrayOf("a", "b").getCyclic(2))
        assertEquals("b", arrayOf("a", "b").getCyclic(-1))
    }

    @Test
    fun typedList() {
        assertEquals(10, intArrayListOf(10, 20).getCyclic(2))
        assertEquals(20, intArrayListOf(10, 20).getCyclic(-1))

        assertEquals(10f, floatArrayListOf(10f, 20f).getCyclic(2))
        assertEquals(20f, floatArrayListOf(10f, 20f).getCyclic(-1))

        assertEquals(10.0, doubleArrayListOf(10.0, 20.0).getCyclic(2))
        assertEquals(20.0, doubleArrayListOf(10.0, 20.0).getCyclic(-1))
    }
}