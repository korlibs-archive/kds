package com.soywiz.kds

import org.junit.Test
import kotlin.test.assertEquals

class LinkedListTest {
    @Test
    fun simple() {
        val l = LinkedList<String>()
        l += listOf("a", "b", "c", "d", "e", "f")
        assertEquals("a,b,c,d,e,f", l.toList().joinToString(","))
        assertEquals("a", l.first)
        assertEquals("f", l.last)
        assertEquals(6, l.size)
        l.removeAt(1)
        assertEquals(5, l.size)
        l.removeAt(l.size - 2)
        assertEquals(4, l.size)
        assertEquals("a,c,d,f", l.toList().joinToString(","))
        l.remove("d")
        assertEquals(3, l.size)
        assertEquals("a,c,f", l.toList().joinToString(","))
        l.retainAll(listOf("a", "f"))
        assertEquals(2, l.size)
        assertEquals("a,f", l.toList().joinToString(","))
        l.removeAll(listOf("a"))
        assertEquals(1, l.size)
        assertEquals("f", l.toList().joinToString(","))
    }

    @Test
    fun grow() {
        val l = LinkedList<String>()
        for (n in 0 until 1000) l.add("$n")
        for (n in 0 until 495) {
            l.removeFirst()
            l.removeLast()
        }
        assertEquals(10, l.size)
        assertEquals("495,496,497,498,499,500,501,502,503,504", l.joinToString(","))
    }
}