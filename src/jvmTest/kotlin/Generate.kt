import java.io.*

object Generate {
    @JvmStatic
    fun main(args: Array<String>) {
        File("src/commonMain/kotlin/com/soywiz/kds/Stack.kt").synchronize()
        File("src/commonMain/kotlin/com/soywiz/kds/Queue.kt").synchronize(includeFloat = false)
        File("src/commonMain/kotlin/com/soywiz/kds/CircularList.kt").synchronize(includeFloat = false)
        File("src/commonMain/kotlin/com/soywiz/kds/Array2.kt").synchronize()
    }

    fun File.synchronize(includeFloat: Boolean = true) {
        val content = this.readText()
        val parts = content.split("// GENERIC\n")
        val head = parts[0].trim()
        val genericSpecific = parts.getOrElse(1) { "" }
        val parts2 = genericSpecific.split("// SPECIFIC - Do not modify from here\n")
        val generic = parts2[0].trim()
        val specific = parts2.getOrElse(1) { "" }
        val intText = generic.replaceTemplate("Int")
        val floatText = if (includeFloat) generic.replaceTemplate("Float") else ""
        val doubleText = generic.replaceTemplate("Double")
        val newSpecific = listOf(intText, floatText, doubleText).filter { it.isNotEmpty() }.joinToString("\n\n")
        this.writeText("$head\n\n// GENERIC\n\n$generic\n\n// SPECIFIC - Do not modify from here\n\n$newSpecific")
    }

    fun String.replaceTemplate(kind: String): String {
        val lkind = kind.toLowerCase()
        return this
            .replace("arrayListOf<T>", "${lkind}ArrayListOf")
            .replace("Array<T>", "${kind}Array")
            .replace(Regex("""(\w+)<T>""")) { kind + it.groupValues[1] }
            .replace(": T", ": $kind")
            .replace("-> T", "-> $kind")
            .replace("as T", "as $kind")
            .replace("(T)", "($kind)")
            .replace("T, ", "$kind, ")
    }
}
