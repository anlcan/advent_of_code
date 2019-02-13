package com.advent.fourteen

/**
 * Created on 2019-02-10.
 */
class Recipes(val input: List<Int>) {
    private val process: MutableList<Int> = mutableListOf()
    private val cooks: List<Cook> = listOf(Cook(0), Cook(1))
    private val initialSize = input.size

    init {
        process.addAll(input)
    }

    companion object {
        fun parse(input: Int): List<Int> {
            return input.toString().toCharArray().map { it.toString().toInt() }
        }
    }

    fun tick() {
        val sum = cooks.map { process[it.index] }.sum()
        process.addAll(parse(sum))
        cooks.forEach { it.index = (it.index + process[it.index] + 1) % process.size }
    }

    override fun toString(): String {
        val map: MutableList<String> = process.map { " $it " }.toMutableList()
        //
        map[cooks[0].index] = "(${map[cooks[0].index].strip()})"
        map[cooks[1].index] = "[${map[cooks[1].index].strip()}]"
        return map.map { String.format("%3s", it) }.joinToString("")
    }

    fun getScore(after: Int, num: Int = 10): String {
        val index = initialSize + after - 2 // 1 for index, the other one i don't kow
        return process.subList(index, index + num)
            .joinToString("", transform = Int::toString)
    }

    fun appearsBefore(input: String):Int{
        return process.joinToString("", transform = Int::toString)
            .indexOf(input)
    }
}

class Cook(var index: Int) {

}
