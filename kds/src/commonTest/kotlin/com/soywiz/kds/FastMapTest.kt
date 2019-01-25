package com.soywiz.kds

import kotlin.test.*

class FastMapTest {
    @Test
    fun testInt() {
        val map = FastIntMap<String>()
        assertEquals(0, map.size)
        map[1] = "a"
        map[2] = "b"
        assertEquals(listOf(1, 2), map.keys.sorted())
        assertEquals(2, map.size)
        assertEquals("a", map[1])
        assertEquals("b", map[2])
        assertEquals(null, map[3])

        map.remove(1)
        assertEquals(1, map.size)
        assertEquals(null, map[1])

        for (n in 10 until 20) map[n] = "$n"

        assertEquals(11, map.size)
        assertEquals("15", map[15])
        map.removeRange(5, 17)

        assertEquals(3, map.size)
        assertEquals(listOf(2, 18, 19), map.keys.sorted())

        map.clear()

        assertEquals(0, map.size)
        assertEquals(listOf(), map.keys.sorted())

        map[0] = "z"
        assertEquals(1, map.size)
        assertEquals(listOf(0), map.keys.sorted())
        assertEquals(listOf("z"), map.values())

        map[1] = "y"
        map[2] = "r"

        map.removeRange(-1, +1)
        assertEquals(1, map.size)
        assertEquals(listOf(2), map.keys.sorted())
        assertEquals(listOf("r"), map.values())
    }

    @Test
    fun testString() {
        val map = FastStringMap<Int>()
        assertEquals(0, map.size)
        map["a"] = 1
        map["b"] = 2
        assertEquals(2, map.size)
        assertEquals(listOf("a", "b"), map.keys.sorted())
        assertEquals(listOf(1, 2), map.values.sorted())
        assertEquals(1, map["a"])
        assertEquals(2, map["b"])
        assertEquals(null, map["c"])
        assertEquals(true, "a" in map)
        assertEquals(false, "c" in map)
        map.clear()
        assertEquals(0, map.size)
        assertEquals(listOf(), map.keys.sorted())
        assertEquals(listOf(), map.values.sorted())
        assertEquals(null, map["a"])

        assertEquals(10, map.getOrPut("a") { 10 })
        assertEquals(10, map.getOrPut("a") { 11 })
        assertEquals(1, map.size)
        map.remove("a")
        map.remove("b")
        assertEquals(0, map.size)
    }
}