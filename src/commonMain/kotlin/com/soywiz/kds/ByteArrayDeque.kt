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
    fun write(bytes: ByteArray, offset: Int = 0, size: Int = bytes.size - offset): Int {
        val out = ensureWrite(size).ring.write(bytes, offset, size)
        if (out > 0) written += out
        return out
    }

    @JvmOverloads
    fun read(bytes: ByteArray, offset: Int = 0, size: Int = bytes.size - offset): Int {
        val out = ring.read(bytes, offset, size)
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
}
