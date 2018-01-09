package com.soywiz.kds

import org.junit.Test
import kotlin.test.assertEquals

class LinkedListJvmTest {
    private fun <T> create() = LinkedList<T>(debug = true)
    //private fun <T> create() = LinkedList<T>(debug = false)

    @Test
    fun simple() {
        val l = create<String>()
        assertEquals(0, l.size)
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
        val l = create<String>()
        for (n in 0 until 1000) l.add("$n")
        for (n in 0 until 495) {
            l.removeFirst()
            l.removeLast()
        }
        assertEquals(10, l.size)
        assertEquals("495,496,497,498,499,500,501,502,503,504", l.joinToString(","))
    }

    @Test
    fun grow2() {
        val l = create<Boolean>()
        for (n in 0 until 1000) l.addFirst(true)
        for (n in 0 until 1000) l.removeFirst()
        for (n in 0 until 1000) l.addFirst(true)
    }

    private fun simpleRemove(count: Int) {
        val l = create<Boolean>()
        for (n in 0 until count) l.addFirst(true); for (n in 0 until count) l.removeFirst()
        for (n in 0 until count) l.addLast(true); for (n in 0 until count) l.removeFirst()
        for (n in 0 until count) l.addFirst(true); for (n in 0 until count) l.removeLast()
        for (n in 0 until count) l.addLast(true); for (n in 0 until count) l.removeLast()
    }

    @Test
    fun simpleRemove1() = simpleRemove(1)

    @Test
    fun simpleRemove2() = simpleRemove(2)

    @Test
    fun simpleRemove4() = simpleRemove(4)

    @Test
    fun simpleRemove15() = simpleRemove(15)

    @Test
    fun simpleRemove16() = simpleRemove(16)

    @Test
    fun simpleRemove32() = simpleRemove(32)


    @Test
    fun mutableIteratorRemove() {
        val l = create<Int>()
        for (n in 0 until 10) l.addLast(n)
        val it = l.iterator()
        while (it.hasNext()) {
            val item = it.next()
            if (item % 2 == 0) {
                it.remove()
            }
        }
        assertEquals("1,3,5,7,9", l.joinToString(","))
    }

    @Test
    fun insertAfter() {
        val l = create<String>()
        val aSlot = l.addLast("a")
        val cSlot = l.addLast("c")
        l.addAfterSlot(aSlot, "b")
        assertEquals("a,b,c", l.joinToString(","))
    }

    @Test
    fun insertAfterLast() {
        val l = create<String>()
        val aSlot = l.addLast("a")
        val cSlot = l.addLast("c")
        l.addAfterSlot(cSlot, "b")
        assertEquals("a,c,b", l.joinToString(","))
    }

    @Test
    fun insertBefore() {
        val l = create<String>()
        val aSlot = l.addLast("a")
        val cSlot = l.addLast("c")
        l.addBeforeSlot(aSlot, "b")
        assertEquals("b,a,c", l.joinToString(","))
    }

    @Test
    fun insertBeforeFirst() {
        val l = create<String>()
        val aSlot = l.addLast("a")
        val cSlot = l.addLast("c")
        l.addBeforeSlot(cSlot, "b")
        assertEquals("a,b,c", l.joinToString(","))
    }
}