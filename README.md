# Korlibs Kotlin Data Structures for JVM, JS and Common

This library includes a set of optimized data structures written in Kotlin Common so they are available in
JVM, JS and future common targets. Those structures are designed to be allocation-efficient and fast, so kds
include specialized versions for primitives like int or double.

# IntArrayList and DoubleArrayList

Kds provides specialized equivalents of ArrayList so it doesn't involve object allocation through boxing.
It uses typed arrays internally to store the ArrayList so it just requires one additional object allocation
per list. It will just allocate a new object when the capacity of the list is exhausted.

# LinkedList

Kds provides a linked list efficient implementation. It doesn't allocate node objects at all,
but has three arrays per list. One with the values, and two linking indices.
When adding/inserting it provides a slot index that you can use to later insert before, after
or delete that "node".
It features constant time (except when growing) insertions, deletions and updates.
Accessing/updating by index has a linear cost. While using the slot requires constant time.

# CircularList, IntCircularList and DoubleCircularList

Kds provides a CircularList implementation. It internally requires just one array per list.
Features constant time for insertions and removals at the beginning and the end of the list.
Constant time for updating or reading by index. It just produces allocations when capacity is exhausted.
Maximum cost for adding or removing elements is N/2 when inserting/removing elements in the exact middle of the list.
Because of its properties is very suitable to implement queues.

# Stack and Queue

Kds provides a stack and a queue implementation and variants for Int and Double.

* Stack<T>, IntStack, DoubleStack
* Queue<T>, IntQueue, DoubleQueue

Stack uses a simple ArrayList or Int/Double variants.
While Queue uses an efficient CircularList.

# BitSet

Kds provides a BitSet structure that works like a BoolArray but packs bits in an IntArray internally so it requires
up to 8x times less space than a BoolArray that potentially uses internally a ByteArray.

# IntSet

A set working with integers without boxing.

# Array2

A bi-dimensional array structure with bi-dimensional indexers.

# PriorityQueue

Provides a PriorityQueue that allows to insert items in a Queue by priority.

# WeakMap + WeakProperty

Provides a WeakMap data structure that internally uses JS's WeakMap and/or JVM's WeakHashMap.
WeakProperty allow to define external/extrinsic properties to objects that are collected once the object is not
referenced anymore.

# Extra + extraProperty

Provides a Extra funtionality to define extrinsic properties to an object that has been decorated with Extra
interface implemented by Extra.Mixin. It just adds a extra hashmap to the object, so it can be used to externally
define properties. The idea is similar to WeakProperty but doesn't require weak references at all. But just works
with objects that implements Extra interface.

# ListReader

A reader for lists