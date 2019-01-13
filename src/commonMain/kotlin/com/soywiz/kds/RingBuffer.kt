package com.soywiz.kds

import kotlin.jvm.*
import kotlin.math.*

class RingBuffer(val bits: Int) {
    val totalSize = 1 shl bits
    private val mask = totalSize - 1
    private val buffer = ByteArray(totalSize)
    private var readPos = 0
    private var writePos = 0
    var availableWrite = totalSize; private set
    var availableRead = 0; private set

    @JvmOverloads
    fun write(bytes: ByteArray, offset: Int = 0, size: Int = bytes.size - offset): Int {
        val toWrite = min(availableWrite, size)
        for (n in 0 until toWrite) {
            buffer[writePos] = bytes[offset + n]
            writePos = (writePos + 1) and mask
        }
        availableRead += toWrite
        availableWrite -= toWrite
        return toWrite
    }

    @JvmOverloads
    fun read(bytes: ByteArray, offset: Int = 0, size: Int = bytes.size - offset): Int {
        val toRead = min(availableRead, size)
        for (n in 0 until toRead) {
            bytes[offset + n] = buffer[readPos]
            readPos = (readPos + 1) and mask
        }
        availableWrite += toRead
        availableRead -= toRead
        return toRead
    }

    fun readByte(): Int {
        if (availableRead <= 0) return -1
        val out = buffer[readPos].toInt() and 0xFF
        readPos = (readPos + 1) and mask
        availableRead--
        availableWrite++
        return out
    }

    fun writeByte(v: Int): Boolean {
        if (availableWrite <= 0) return false
        buffer[writePos] = v.toByte()
        writePos = (writePos + 1) and mask
        availableWrite--
        availableRead++
        return true
    }

    fun clear() {
        readPos = 0
        writePos = 0
        availableRead = 0
        availableWrite = totalSize
    }
}

class ShortRingBuffer(val bits: Int) {
    val totalSize = 1 shl bits
    private val mask = totalSize - 1
    private val buffer = ShortArray(totalSize)
    private var readPos = 0
    private var writePos = 0
    var availableWrite = totalSize; private set
    var availableRead = 0; private set

    @JvmOverloads
    fun write(bytes: ShortArray, offset: Int = 0, size: Int = bytes.size - offset): Int {
        val toWrite = min(availableWrite, size)
        for (n in 0 until toWrite) {
            buffer[writePos] = bytes[offset + n]
            writePos = (writePos + 1) and mask
        }
        availableRead += toWrite
        availableWrite -= toWrite
        return toWrite
    }

    @JvmOverloads
    fun read(bytes: ShortArray, offset: Int = 0, size: Int = bytes.size - offset): Int {
        val toRead = min(availableRead, size)
        for (n in 0 until toRead) {
            bytes[offset + n] = buffer[readPos]
            readPos = (readPos + 1) and mask
        }
        availableWrite += toRead
        availableRead -= toRead
        return toRead
    }

    fun clear() {
        readPos = 0
        writePos = 0
        availableRead = 0
        availableWrite = totalSize
    }
}

class IntRingBuffer(val bits: Int) {
    val totalSize = 1 shl bits
    private val mask = totalSize - 1
    private val buffer = IntArray(totalSize)
    private var readPos = 0
    private var writePos = 0
    var availableWrite = totalSize; private set
    var availableRead = 0; private set

    @JvmOverloads
    fun write(bytes: IntArray, offset: Int = 0, size: Int = bytes.size - offset): Int {
        val toWrite = min(availableWrite, size)
        for (n in 0 until toWrite) {
            buffer[writePos] = bytes[offset + n]
            writePos = (writePos + 1) and mask
        }
        availableRead += toWrite
        availableWrite -= toWrite
        return toWrite
    }

    @JvmOverloads
    fun read(bytes: IntArray, offset: Int = 0, size: Int = bytes.size - offset): Int {
        val toRead = min(availableRead, size)
        for (n in 0 until toRead) {
            bytes[offset + n] = buffer[readPos]
            readPos = (readPos + 1) and mask
        }
        availableWrite += toRead
        availableRead -= toRead
        return toRead
    }

    fun clear() {
        readPos = 0
        writePos = 0
        availableRead = 0
        availableWrite = totalSize
    }
}

class FloatRingBuffer(val bits: Int) {
    val totalSize = 1 shl bits
    private val mask = totalSize - 1
    private val buffer = FloatArray(totalSize)
    private var readPos = 0
    private var writePos = 0
    var availableWrite = totalSize; private set
    var availableRead = 0; private set

    @JvmOverloads
    fun write(bytes: FloatArray, offset: Int = 0, size: Int = bytes.size - offset): Int {
        val toWrite = min(availableWrite, size)
        for (n in 0 until toWrite) {
            buffer[writePos] = bytes[offset + n]
            writePos = (writePos + 1) and mask
        }
        availableRead += toWrite
        availableWrite -= toWrite
        return toWrite
    }

    @JvmOverloads
    fun read(bytes: FloatArray, offset: Int = 0, size: Int = bytes.size - offset): Int {
        val toRead = min(availableRead, size)
        for (n in 0 until toRead) {
            bytes[offset + n] = buffer[readPos]
            readPos = (readPos + 1) and mask
        }
        availableWrite += toRead
        availableRead -= toRead
        return toRead
    }

    fun clear() {
        readPos = 0
        writePos = 0
        availableRead = 0
        availableWrite = totalSize
    }
}
