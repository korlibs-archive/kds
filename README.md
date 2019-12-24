<h2 align="center"><img alt="kds" src="docs/kds-nomargin-256.png" /></h2>

<p align="center">
Kds is a Data Structure library for Multiplatform Kotlin 1.3.
It includes a set of optimized data structures written in Kotlin Common so they are available in
JVM, JS and future multiplatform targets. Those structures are designed to be allocation-efficient and fast, so Kds
include specialized versions for primitives like <code>Int</code> or <code>Double</code>.
</p>

<!-- BADGES -->
<p align="center">
	<a href="https://github.com/korlibs/kds/actions"><img alt="Build Status" src="https://github.com/korlibs/kds/workflows/CI/badge.svg" /></a>
	<a href="https://bintray.com/korlibs/korlibs/kds"><img alt="Maven Version" src="https://img.shields.io/bintray/v/korlibs/korlibs/kds.svg?style=flat&label=maven" /></a>
	<a href="https://slack.soywiz.com/"><img alt="Slack" src="https://img.shields.io/badge/chat-on%20slack-green?style=flat&logo=slack" /></a>
</p>
<!-- /BADGES -->

<!-- SUPPORT -->
<h2 align="center">Support kds</h2>
<p align="center">
If you like kds, or want your company logo here, please consider <a href="https://github.com/sponsors/soywiz">becoming a sponsor ★</a>,<br />
in addition to ensure the continuity of the project, you will get exclusive content.
</p>
<!-- /SUPPORT -->

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
def kdsVersion = "1.9.2"

repositories {
    maven { url "https://dl.bintray.com/korlibs/korlibs" }
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
