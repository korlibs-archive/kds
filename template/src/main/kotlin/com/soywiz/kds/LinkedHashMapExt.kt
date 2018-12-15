package com.soywiz.kds

fun <K, V> linkedHashMapOf(vararg pairs: Pair<K, V>): LinkedHashMap<K, V> =
	LinkedHashMap<K, V>().also { for ((key, value) in pairs) it[key] = value }

fun <K, V> Iterable<Pair<K, V>>.toLinkedMap(): LinkedHashMap<K, V> =
	LinkedHashMap<K, V>().also { for ((key, value) in this) it[key] = value }
