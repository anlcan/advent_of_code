package com.advent.fifteen

/**
 * Created on 2019-02-13.
 */
data class Point(val x: Int, val y: Int, var weight: Int = 0) : Comparable<Point> {
    override fun compareTo(other: Point): Int {
        return if (other.y == this.y) {
            this.x.compareTo(other.x)
        } else {
            this.y.compareTo(other.y)
        }
    }

    fun range(): List<Point> {
        return listOf(
            Point(this.x, this.y - 1),
            Point(this.x - 1, this.y),
            Point(this.x, this.y + 1),
            Point(this.x + 1, this.y)
        )
    }
}

class Combat(input: List<String>) {

    private val battleField = mutableListOf<String>()

    private val elves: MutableList<Unit> = mutableListOf()
    private val goblins: MutableList<Unit> = mutableListOf()

    init {

        for ((y, line) in input.withIndex()) {
            val st = StringBuilder()
            for ((x, point) in line.withIndex()) {

                when (point) {
                    'E' -> {
                        elves.add(Unit(x, y, 'E')); st.append(".")
                    }
                    'G' -> {
                        goblins.add(Unit(x, y, 'G')); st.append(".")
                    }
                    else -> st.append(point)
                }
            }
            battleField.add(st.toString())
        }
    }

    fun units(): MutableList<Unit> {
        val all = elves.toMutableList()
        all.addAll(goblins)
        return all
    }

    fun game() {
        val units = units()
        units.sort()
        units.map { turn(it) }.any()
    }

    fun traj(a: Point, b: Point, acc: List<Point> = mutableListOf()): List<List<Point>> {

        if (acc.isEmpty()) return
        val result1 = mutableListOf<Point>()
        val result2 = mutableListOf<Point>()

        val rangeX = if (a.x > b.x) IntRange(b.x, a.x) else IntRange(a.x, b.x)
        for (x in rangeX) {
            result1.add(Point(x, a.y))
            result2.add(Point(x, b.y))
        }

        val rangeY = if (a.y > b.y) IntRange(b.y, a.y) else IntRange(a.y, b.y)
        for (y in rangeY) {
            result1.add(Point(a.x, y))
            result2.add(Point(b.x, y))
        }

        print(result1)
        print(result2)

        return mutableListOf(result1, result2)
    }

    /**
     * To move, the unit first considers the squares that are in range and determines which of those squares it
     * could reach in the fewest steps.
     * A step is a single movement to any adjacent (immediately up, down, left, or right) open (.) square.
     * Units cannot move into walls or other units. The unit does this while considering the current positions of units
     * and does not do any prediction about where units will be later.
     * If the unit cannot reach (find an open path to) any of the squares that are in range, it ends its turn.
     * If multiple squares are in range and tied for being reachable in the fewest steps,
     * the square which is first in reading order is chosen. For example:a
     */
    fun reachableShortestPath(a: Unit, b: Unit, input: List<String> = this.current()): List<Point>? {

        val possiblePath = b.range().map { point -> traj(a.point, point) }
            .flatten()
            .filter { pointList -> pointList.all { point -> safeGet(point, input) == '.' } }
            .sortedBy { it.size }


        return if (possiblePath.isNotEmpty()) possiblePath.first() else null
    }

    fun turn(unit: Unit): Boolean {

        val adv = if (unit.sign == 'E') goblins else elves

        val adj = unit.range().map { point -> adv.find { a -> a.point == point } }
            .filterNotNull()
            .sortedBy { it.hitPoint }

        if (adj.isNotEmpty()) {
            adj.first().hitPoint = adj.first().hitPoint - 3
            return true
        }

        val possibleMoves = adv.map { reachableShortestPath(unit, it) }.filterNotNull()
        if (possibleMoves.isNotEmpty()) {
            val p = possibleMoves[0][0]
            unit.point = p
            return true
        }

        return false
    }


    private fun current(): List<String> {
        val copy = battleField.toMutableList()

        elves.forEach { set(it.point, it.sign, copy) }
        goblins.forEach { set(it.point, it.sign, copy) }

        return copy
    }

    fun print() {
        current().forEach { println(it) }
    }

    fun get(p: Point, input: List<String> = this.battleField): Char {
        return input[p.y][p.x]
    }

    fun safeGet(p: Point, input: List<String> = this.battleField): Char? {
        return try {
            get(p, input)
        } catch (e: IndexOutOfBoundsException) {
            null
        }
    }

    fun set(p: Point, c: Char, target: MutableList<String> = battleField) {
        val s: String = target[p.y]
        target[p.y] = s.replaceRange(p.x, p.x + 1, "$c")
    }

}

class Unit(var x: Int, var y: Int, val sign: Char) : Comparable<Unit> {
    var hitPoint: Int = 200
    val attackPoiwer: Int = 3
    var point: Point = Point(x, y)

    override fun compareTo(other: Unit): Int {
        return this.point.compareTo(other.point)
    }

    fun range(): List<Point> {
        return point.range()
    }
}


