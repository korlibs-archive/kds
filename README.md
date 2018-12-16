# ![Kds](docs/kds-nomargin-256.png)

Klock is a Date Structure library for Multiplatform Kotlin 1.3.
It includes a set of optimized data structures written in Kotlin Common so they are available in
JVM, JS and future multiplatform targets. Those structures are designed to be allocation-efficient and fast, so Kds
include specialized versions for primitives like `Int` or `Double`.

[![Build Status](https://travis-ci.org/korlibs/kds.svg?branch=master)](https://travis-ci.org/korlibs/kds)
[![Maven Version](https://img.shields.io/github/tag/korlibs/kds.svg?style=flat&label=maven)](http://search.maven.org/#search%7Cga%7C1%7Ca%3A%22kds%22)

### Full Documentation: https://korlibs.soywiz.com/kds/

### Some samples:

```kotlin
// Case Insensitive Map
val map = mapOf("hELLo" to 1, "World" to 2).toCaseInsensitiveMap()
println(map["hello"])

// BitSet
val array = BitSet(100) // Stores 100 bits
array[99] = true

// TypedArrayList
val v20 = intArrayListOf(10, 20).getCyclic(-1)

// Deque
val deque = IntDeque().apply {
    addFirst(n)
    removeFirst()
    addLast(n)
}

// CacheMap
val cache = CacheMap<String, Int>(maxSize = 2).apply {
    this["a"] = 1
    this["b"] = 2
    this["c"] = 3
    assertEquals("{b=2, c=3}", this.toString())
}

// IntIntMap
val m = IntIntMap().apply {
    this[0] = 98
}

// Pool
val pool = Pool { Demo() }
pool.alloc { demo ->
    println("Temporarilly allocated $demo")
}

// Priority Queue
val pq = IntPriorityQueue()
pq.add(10)
pq.add(5)
pq.add(15)
assertEquals(5, pq.removeHead())

// Extra Properties
class Demo : Extra by Extra.Mixin() { val default = 9 }
var Demo.demo by Extra.Property { 0 }
var Demo.demo2 by Extra.PropertyThis<Demo, Int> { default }
val demo = Demo()
assertEquals(0, demo.demo)
assertEquals(9, demo.demo2)
demo.demo = 7
assertEquals(7, demo.demo)
assertEquals("{demo=7, demo2=9}", demo.extra.toString())

// mapWhile
val iterator = listOf(1, 2, 3).iterator()
assertEquals(listOf(1, 2, 3), mapWhile({ iterator.hasNext() }) { iterator.next()})

// And much more!
```

### Usage with gradle:
```kotlin
def kdsVersion = "1.0.0"

repositories {
    maven { url "https://dl.bintray.com/soywiz/soywiz" }
}

dependencies {
    // For multiplatform projects
    implementation "com.soywiz:kds:$kdsVersion"
    
    // For JVM/Android only
    implementation "com.soywiz:kds-jvm:$kdsVersion"
    // For JS only
    implementation "com.soywiz:kds-js:$kdsVersion"
}

// settigs.gradle
enableFeaturePreview('GRADLE_METADATA')
```
