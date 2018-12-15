package com.soywiz.kds

import kotlin.test.*

class CacheMapTest {
    @Test
    fun test() {
        val freeLog = arrayListOf<String>()
        val cache = CacheMap<String, Int>(maxSize = 2, free = { k, v ->
            freeLog += "$k:$v"
        })
        cache["a"] = 1
        cache["b"] = 2
        cache["c"] = 3
        assertEquals("{b=2, c=3}", cache.toString())
        assertEquals("a:1", freeLog.joinToString(", "))

        assertEquals(false, "a" in cache)
        assertEquals(true, "b" in cache)
        assertEquals(true, "c" in cache)
    }
}