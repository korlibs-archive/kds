package com.soywiz.kds

internal class MixedArrayList<T>(val shape: Shape) {
    enum class Shape { OBJ, INT }

    private var _objects: ArrayList<T>? = null
    private var _ints: IntArrayList? = null
    //private var doubles: DoubleArrayList? = null

    private fun shape(shape: Shape) = this.apply {
        if (this.shape != shape) {
            throw IllegalArgumentException("MixedArrayList was of type ${this.shape} but used $shape")
        }
    }

    private val objects: ArrayList<T>
        get() {
            shape(Shape.OBJ)
            if (_objects == null) _objects = arrayListOf()
            return _objects!!
        }

    private val ints: IntArrayList
        get() {
            shape(Shape.INT)
            if (_ints == null) _ints = IntArrayList()
            return _ints!!
        }

    val length: Int
        get() = when (shape) {
            Shape.OBJ -> objectSize()
            Shape.INT -> intSize()
        }

    fun clear() = when (shape) {
        Shape.OBJ -> objects.clear()
        Shape.INT -> ints.clear()
    }

    fun removeAt(index: Int): Unit = when (shape) {
        Shape.OBJ -> Unit.apply { objectRemoveAt(index) }
        Shape.INT -> intRemoveAt(index)
    }

    fun isObjectArray() = shape == Shape.OBJ
    fun isIntArray() = shape == Shape.INT

    fun objectSize() = objects.size
    fun objectClear() = objects.clear()
    fun objectAdd(value: T) = objects.add(value)
    fun objectRemoveAt(index: Int) = objects.removeAt(index)
    fun objectGet(index: Int) = objects[index]
    fun objectSet(index: Int, value: T) = objects.set(index, value)

    fun intSize() = ints.size
    fun intClear() = ints.clear()
    fun intAdd(value: Int) = ints.add(value)
    fun intRemoveAt(index: Int) = ints.removeAt(index)
    fun intGet(index: Int) = ints[index]
    fun intSet(index: Int, value: Int) = ints.set(index, value)
}