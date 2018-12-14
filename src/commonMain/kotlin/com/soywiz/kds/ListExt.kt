package com.soywiz.kds

import com.soywiz.kds.internal.umod

fun <T> List<T>.getCyclic(index: Int) = this[index umod this.size]
fun <T> Array<T>.getCyclic(index: Int) = this[index umod this.size]
