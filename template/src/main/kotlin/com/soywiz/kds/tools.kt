package com.soywiz.kds

class IntArrayList : Iterable<Int> {
    val size: Int get() = TODO()
    external operator fun plusAssign(value: Int)
    external override fun iterator(): Iterator<Int>
}