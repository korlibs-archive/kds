package com.soywiz.kds

import org.junit.Test
import kotlin.test.assertEquals

class QueueTest {
    @Test
    fun name() {
        val queue = Queue<Boolean>()
        for (n in 0 until 1025) queue.enqueue(true)
        for (n in 0 until 1025) assertEquals(true, queue.dequeue())
    }
}