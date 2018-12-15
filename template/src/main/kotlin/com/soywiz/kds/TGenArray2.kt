package com.soywiz.kds

inline fun <TGen : Any, RGen : Any> Array2<TGen>.map2(gen: (x: Int, y: Int, v: TGen) -> RGen): Array2<RGen> =
    Array2<RGen>(width, height) {
        val x = it % width
        val y = it / width
        gen(x, y, this[x, y])
    }

// GENERIC

@Suppress("NOTHING_TO_INLINE", "RemoveExplicitTypeArguments")
data class Array2<TGen>(val width: Int, val height: Int, val data: Array<TGen>) : Iterable<TGen> {
    companion object {
        inline operator fun <TGen : Any> invoke(width: Int, height: Int, gen: (n: Int) -> TGen): Array2<TGen> =
            Array2<TGen>(width, height, Array<Any>(width * height) { gen(it) } as Array<TGen>)

        inline fun <TGen : Any> withGen(width: Int, height: Int, gen: (x: Int, y: Int) -> TGen): Array2<TGen> =
            Array2<TGen>(width, height, Array<Any>(width * height) { gen(it % width, it / width) } as Array<TGen>)

        inline operator fun <TGen : Any> invoke(rows: List<List<TGen>>): Array2<TGen> {
            val width = rows[0].size
            val height = rows.size
            val anyCell = rows[0][0]
            return (Array2<TGen>(width, height) { anyCell }).apply { set(rows) }
        }

        inline operator fun <TGen : Any> invoke(
            map: String,
            marginChar: Char = '\u0000',
            gen: (char: Char, x: Int, y: Int) -> TGen
        ): Array2<TGen> {
            val lines = map.lines()
                .map {
                    val res = it.trim()
                    if (res.startsWith(marginChar)) {
                        res.substring(0, res.length)
                    } else {
                        res
                    }
                }
                .filter { it.isNotEmpty() }
            val width = lines.map { it.length }.max() ?: 0
            val height = lines.size

            return Array2<TGen>(width, height) { n ->
                val x = n % width
                val y = n / width
                gen(lines.getOrNull(y)?.getOrNull(x) ?: ' ', x, y)
            }
        }

        inline operator fun <TGen : Any> invoke(
            map: String,
            default: TGen,
            transform: Map<Char, TGen>
        ): Array2<TGen> {
            return invoke(map) { c, x, y -> transform[c] ?: default }
        }

        inline fun <TGen : Any> fromString(
            maps: Map<Char, TGen>,
            default: TGen,
            code: String,
            marginChar: Char = '\u0000'
        ): Array2<TGen> {
            return invoke(code, marginChar = marginChar) { c, _, _ -> maps[c] ?: default }
        }
    }

    fun set(rows: List<List<TGen>>) {
        var n = 0
        for (y in rows.indices) {
            val row = rows[y]
            for (x in row.indices) {
                this.data[n++] = row[x]
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        return (other is Array2<*/*TGen*/>) && this.width == other.width && this.height == other.height && this.data.contentEquals(
            other.data
        )
    }

    override fun hashCode(): Int = width + height + data.hashCode()

    private inline fun index(x: Int, y: Int) = y * width + x

    operator fun get(x: Int, y: Int): TGen = data[index(x, y)]
    operator fun set(x: Int, y: Int, value: TGen): Unit = run { data[index(x, y)] = value }
    fun tryGet(x: Int, y: Int): TGen? = if (inside(x, y)) data[index(x, y)] else null
    fun trySet(x: Int, y: Int, value: TGen): Unit = run { if (inside(x, y)) data[index(x, y)] = value }

    fun inside(x: Int, y: Int): Boolean = x >= 0 && y >= 0 && x < width && y < height

    operator fun contains(v: TGen): Boolean = this.data.contains(v)

    inline fun each(callback: (x: Int, y: Int, v: TGen) -> Unit) {
        var n = 0
        for (y in 0 until height) {
            for (x in 0 until width) {
                callback(x, y, data[n++])
            }
        }
    }

    inline fun fill(gen: (old: TGen) -> TGen) {
        var n = 0
        for (y in 0 until height) {
            for (x in 0 until width) {
                data[n] = gen(data[n])
                n++
            }
        }
    }

    fun getPositionsWithValue(value: TGen) =
        data.indices.filter { data[it] == value }.map { Pair(it % width, it / width) }

    fun clone() = Array2<TGen>(width, height, data.copyOf())

    fun dump() {
        for (y in 0 until height) {
            for (x in 0 until width) {
                print(this[x, y])
            }
            println()
        }
    }

    override fun iterator(): Iterator<TGen> = data.iterator()

    fun toStringList(charMap: (TGen) -> Char, margin: String = ""): List<String> {
        return (0 until height).map { y ->
            margin + String(CharArray(width) { x -> charMap(this[x, y]) })
        }
    }

    fun toString(margin: String = "", charMap: (TGen) -> Char): String =
        toStringList(charMap, margin = margin).joinToString("\n")

    fun toString(map: Map<TGen, Char>, margin: String = ""): String = toString(margin = margin) { map[it] ?: ' ' }
}
