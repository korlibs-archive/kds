package com.soywiz.kds

import org.junit.Test
import java.util.*
import kotlin.test.assertEquals

class IntMap2JvmTest {
    @Test
    fun simple() {
        val m = IntMap<String>()
        assertEquals(0, m.size)
        assertEquals(null, m[0])

        m[0] = "test"
        assertEquals(1, m.size)
        assertEquals("test", m[0])
        assertEquals(null, m[1])

        m[0] = "test2"
        assertEquals(1, m.size)
        assertEquals("test2", m[0])
        assertEquals(null, m[1])

        m.remove(0)
        assertEquals(0, m.size)
        assertEquals(null, m[0])
        assertEquals(null, m[1])

        m.remove(0)
    }

    @Test
    fun name2() {
        val m = IntMap<Int>()
        for (n in 0 until 1000) m[n] = n * 1000
        for (n in 0 until 1000) assertEquals(n * 1000, m[n])
        assertEquals(null, m[-1])
        assertEquals(null, m[1001])
    }

    @Test
    fun name3() {
        val m = IntMap<String>()
        //val m = IntMap<String>()
        //val m = HashMap<Int, String>()
        for (n in 0 until 100000) m[n] = "a$n"
        //for (key in m.values) {
        //println(key)
        //}
    }

    @Test
    fun name4() {
        val ref = hashMapOf<Int, String>()
        val imp = IntMap<String>()
        val rand1 = Random(0L)
        for (n in 0 until 10000) {
            val key = rand1.nextInt()
            ref[key] = "n$n"
            imp[key] = "n$n"
        }
        val rand2 = Random(0L)
        for (n in 0 until 10000) {
            val key = rand2.nextInt()
            assertEquals(ref[key], imp[key])
        }
    }
}