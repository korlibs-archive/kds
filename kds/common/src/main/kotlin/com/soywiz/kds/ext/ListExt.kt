package com.soywiz.kds.ext

fun <T> List<T>.getCyclic(index: Int) = this[index % this.size]