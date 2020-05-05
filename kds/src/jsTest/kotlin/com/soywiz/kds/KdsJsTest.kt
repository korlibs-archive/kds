package com.soywiz.kds

import kotlin.test.*

class KdsJsTest {
    @Test
    fun test() {
        if (js("(typeof window != 'undefined')")) {
            assertEquals(false, true)
        } else {
            assertEquals(true, true)
        }
    }
}
