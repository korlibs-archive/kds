package com.soywiz.kds

import com.soywiz.kds.internal.*
import kotlin.jvm.*

class ByteArrayDeque(val initialBits: Int = 10) {
    private var ring = RingBuffer(initialBits)
    private val tempBuffer = ByteArray(1024)

    var written: Long = 0; private set
    var read: Long = 0; private set
    val availableWriteWithoutAllocating get() = ring.availableWrite
    val availableRead get() = ring.availableRead

    @JvmOverloads
    fun writeHead(buffer: ByteArray, offset: Int = 0, size: Int = buffer.size - offset): Int {
        val out = ensureWrite(size).ring.writeHead(buffer, offset, size)
        if (out > 0) written += out
        return out
    }

    @JvmOverloads
    fun write(buffer: ByteArray, offset: Int = 0, size: Int = buffer.size - offset): Int {
        val out = ensureWrite(size).ring.write(buffer, offset, size)
        if (out > 0) written += out
        return out
    }

    @JvmOverloads
    fun read(buffer: ByteArray, offset: Int = 0, size: Int = buffer.size - offset): Int {
        val out = ring.read(buffer, offset, size)
        if (out > 0) read += out
        return out
    }

    fun readByte(): Int = ring.readByte()
    fun writeByte(v: Int): Boolean = ensureWrite(1).ring.writeByte(v)

    private fun ensureWrite(count: Int): ByteArrayDeque {
        if (count > ring.availableWrite) {
            val minNewSize = ring.availableRead + count
            val newBits = ilog2(minNewSize) + 2
            val newRing = RingBuffer(newBits)
            while (ring.availableRead > 0) {
                val read = ring.read(tempBuffer, 0, tempBuffer.size)
                newRing.write(tempBuffer, 0, read)
            }
            this.ring = newRing
        }
        return this
    }

    fun clear() {
        ring.clear()
    }

    val hasMoreToWrite get() = ring.availableWrite > 0
    val hasMoreToRead get() = ring.availableRead > 0
    fun readOne() = run {
        read(tempBuffer, 0, 1)
        tempBuffer[0]
    }
    fun writeOne(value: Byte) {
        tempBuffer[0] = value
        write(tempBuffer, 0, 1)
    }

    override fun hashCode(): Int = ring.hashCode()
    override fun equals(other: Any?): Boolean = (other is ByteArrayDeque) && this.ring == other.ring
}

class ShortArrayDeque(val initialBits: Int = 10) {
    private var ring = ShortRingBuffer(initialBits)
    private val tempBuffer = ShortArray(1024)

    var written: Long = 0; private set
    var read: Long = 0; private set
    val availableWriteWithoutAllocating get() = ring.availableWrite
    val availableRead get() = ring.availableRead

    @JvmOverloads
    fun writeHead(buffer: ShortArray, offset: Int = 0, size: Int = buffer.size - offset): Int {
        val out = ensureWrite(size).ring.writeHead(buffer, offset, size)
        if (out > 0) written += out
        return out
    }

    @JvmOverloads
    fun write(buffer: ShortArray, offset: Int = 0, size: Int = buffer.size - offset): Int {
        val out = ensureWrite(size).ring.write(buffer, offset, size)
        if (out > 0) written += out
        return out
    }

    @JvmOverloads
    fun read(buffer: ShortArray, offset: Int = 0, size: Int = buffer.size - offset): Int {
        val out = ring.read(buffer, offset, size)
        if (out > 0) read += out
        return out
    }

    private fun ensureWrite(count: Int): ShortArrayDeque {
        if (count > ring.availableWrite) {
            val minNewSize = ring.availableRead + count
            val newBits = ilog2(minNewSize) + 2
            val newRing = ShortRingBuffer(newBits)
            while (ring.availableRead > 0) {
                val read = ring.read(tempBuffer, 0, tempBuffer.size)
                newRing.write(tempBuffer, 0, read)
            }
            this.ring = newRing
        }
        return this
    }

    fun clear() {
        ring.clear()
    }

    val hasMoreToWrite get() = ring.availableWrite > 0
    val hasMoreToRead get() = ring.availableRead > 0
    fun readOne() = run {
        read(tempBuffer, 0, 1)
        tempBuffer[0]
    }
    fun writeOne(value: Short) {
        tempBuffer[0] = value
        write(tempBuffer, 0, 1)
    }

    override fun hashCode(): Int = ring.hashCode()
    override fun equals(other: Any?): Boolean = (other is ShortArrayDeque) && this.ring == other.ring
}


class IntArrayDeque(val initialBits: Int = 10) {
    private var ring = IntRingBuffer(initialBits)
    private val tempBuffer = IntArray(1024)

    var written: Long = 0; private set
    var read: Long = 0; private set
    val availableWriteWithoutAllocating get() = ring.availableWrite
    val availableRead get() = ring.availableRead

    @JvmOverloads
    fun writeHead(buffer: IntArray, offset: Int = 0, size: Int = buffer.size - offset): Int {
        val out = ensureWrite(size).ring.writeHead(buffer, offset, size)
        if (out > 0) written += out
        return out
    }

    @JvmOverloads
    fun write(buffer: IntArray, offset: Int = 0, size: Int = buffer.size - offset): Int {
        val out = ensureWrite(size).ring.write(buffer, offset, size)
        if (out > 0) written += out
        return out
    }

    @JvmOverloads
    fun read(buffer: IntArray, offset: Int = 0, size: Int = buffer.size - offset): Int {
        val out = ring.read(buffer, offset, size)
        if (out > 0) read += out
        return out
    }

    private fun ensureWrite(count: Int): IntArrayDeque {
        if (count > ring.availableWrite) {
            val minNewSize = ring.availableRead + count
            val newBits = ilog2(minNewSize) + 2
            val newRing = IntRingBuffer(newBits)
            while (ring.availableRead > 0) {
                val read = ring.read(tempBuffer, 0, tempBuffer.size)
                newRing.write(tempBuffer, 0, read)
            }
            this.ring = newRing
        }
        return this
    }

    fun clear() {
        ring.clear()
    }

    val hasMoreToWrite get() = ring.availableWrite > 0
    val hasMoreToRead get() = ring.availableRead > 0
    fun readOne() = run {
        read(tempBuffer, 0, 1)
        tempBuffer[0]
    }
    fun writeOne(value: Int) {
        tempBuffer[0] = value
        write(tempBuffer, 0, 1)
    }

    override fun hashCode(): Int = ring.hashCode()
    override fun equals(other: Any?): Boolean = (other is IntArrayDeque) && this.ring == other.ring
}


class FloatArrayDeque(val initialBits: Int = 10) {
    private var ring = FloatRingBuffer(initialBits)
    private val tempBuffer = FloatArray(1024)

    var written: Long = 0; private set
    var read: Long = 0; private set
    val availableWriteWithoutAllocating get() = ring.availableWrite
    val availableRead get() = ring.availableRead

    @JvmOverloads
    fun writeHead(buffer: FloatArray, offset: Int = 0, size: Int = buffer.size - offset): Int {
        val out = ensureWrite(size).ring.writeHead(buffer, offset, size)
        if (out > 0) written += out
        return out
    }

    @JvmOverloads
    fun write(buffer: FloatArray, offset: Int = 0, size: Int = buffer.size - offset): Int {
        val out = ensureWrite(size).ring.write(buffer, offset, size)
        if (out > 0) written += out
        return out
    }

    @JvmOverloads
    fun read(buffer: FloatArray, offset: Int = 0, size: Int = buffer.size - offset): Int {
        val out = ring.read(buffer, offset, size)
        if (out > 0) read += out
        return out
    }

    private fun ensureWrite(count: Int): FloatArrayDeque {
        if (count > ring.availableWrite) {
            val minNewSize = ring.availableRead + count
            val newBits = ilog2(minNewSize) + 2
            val newRing = FloatRingBuffer(newBits)
            while (ring.availableRead > 0) {
                val read = ring.read(tempBuffer, 0, tempBuffer.size)
                newRing.write(tempBuffer, 0, read)
            }
            this.ring = newRing
        }
        return this
    }

    fun clear() {
        ring.clear()
    }

    val hasMoreToWrite get() = ring.availableWrite > 0
    val hasMoreToRead get() = ring.availableRead > 0
    fun readOne() = run {
        read(tempBuffer, 0, 1)
        tempBuffer[0]
    }
    fun writeOne(value: Float) {
        tempBuffer[0] = value
        write(tempBuffer, 0, 1)
    }

    override fun hashCode(): Int = ring.hashCode()
    override fun equals(other: Any?): Boolean = (other is FloatArrayDeque) && this.ring == other.ring
}
