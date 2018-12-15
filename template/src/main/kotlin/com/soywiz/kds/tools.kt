package com.soywiz.kds

external fun <T : Comparable<T>> comparator(): Comparator<T>

external fun <K, V> Iterable<Pair<K, V>>.toLinkedMap(): LinkedHashMap<K, V>
