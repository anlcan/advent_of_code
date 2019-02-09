package com.advent.thirteen

/**
 * Created on 2019-02-07.
 */
class CircularList<T>(private val list: List<T>) : List<T> by list {

    override fun get(index: Int): T =
        list[index.safely()]

    // Other overrides removed for brevity.
    /**
     * Get a [kotlin.collections.ListIterator] starting at the specified [index]
     *
     * If the [index] is negative it is interpreted as an offset from the end of
     * the list. If the [index] is positive and beyond the bounds of the underlying list,
     * it wraps around again from the start of the list.
     *
     * @sample samples.Cirkle.CircularList.listIterator
     */
    override fun listIterator(index: Int): ListIterator<T> =
        list.listIterator(index.safely())

    /**
     * Get a List bound by [fromIndex] inclusive to [toIndex] exclusive
     *
     * If [fromIndex] or [toIndex] is negative they are interpreted as an offset from the end of
     * the list. If the [fromIndex] or [toIndex] is positive and beyond the bounds of the underlying list,
     * it wraps around again from the start of the list.
     *
     * @sample samples.Cirkle.CircularList.subList
     */
    override fun subList(fromIndex: Int, toIndex: Int): List<T> =
        list.subList(fromIndex.safely(), toIndex.safely())

    /**
     * Returns a String representation of the object.
     */
    override fun toString(): String =
        list.toString()

    private fun Int.safely(): Int =
        if (this < 0) (this % size + size) % size
        else this % size

    fun next(t: T): T {
        val index = this.indexOf(t)
        return this[index + 1]
    }

    fun prev(t: T): T {
        val index = this.indexOf(t)
        return this[index - 1]
    }

}

fun <T> List<T>.circular(): CircularList<T> = CircularList(this)

enum class Direction(val sign: Char) {
    UP('^'), LEFT('<'), DOWN('v'), RIGHT('>');

    companion object {
        private val circular = Direction.values().asList().circular()
        fun parse(input: Char): Direction? {
            return values().find { it.sign == input }
        }
    }

    fun turn(nextTurn: Turn): Direction {
        return when (nextTurn) {

            Turn.LEFT -> circular.next(this)
            Turn.STRAIGHT -> this
            Turn.RIGHT -> circular.prev(this)
        }
    }

    fun toTrack(): Track {
        return when (this) {
            UP, DOWN -> Track.VERTICAL
            RIGHT, LEFT -> Track.HORIZONTAL
        }
    }
}

enum class Turn {
    LEFT, STRAIGHT, RIGHT;

    companion object {
        val circular = Turn.values().asList().circular()
    }

    fun next(): Turn =
        circular.next(this)
}

data class Point(val x: Int, val y: Int)

class Cart(x: Int, y: Int = 0, var direction: Direction) : Comparable<Cart> {
    private var nextTurn: Turn = Turn.LEFT
    var point = Point(x, y)
    var onTrack = true
    public val traj = mutableListOf<Point>()

    /**
    Each time a cart has the option to turn (by arriving at any intersection),
    it turns left the first time, goes straight the second time, turns right
     */
    fun turn(track:Track) {
        if (track == Track.INTERSECTION) {
            direction = direction.turn(nextTurn)
            nextTurn = nextTurn.next()
        } else {
            direction = track.turn(direction)
        }


    }

    override fun compareTo(other: Cart): Int {
        return if (other.point.y == this.point.y) {
            this.point.x.compareTo(other.point.x)
        } else {
            this.point.y.compareTo(other.point.y)
        }
    }


    fun next(): Point {
        return when (direction) {
            Direction.UP -> Point(point.x, point.y - 1)
            Direction.LEFT -> Point(point.x - 1, point.y)
            Direction.DOWN -> Point(point.x, point.y + 1)
            Direction.RIGHT -> Point(point.x + 1, point.y)
        }
    }

    fun move(p: Point) {
        traj.add(point)
        point = p

    }
}

enum class Track(val sign: Char) {
    INTERSECTION('+'),
    VERTICAL('|'), HORIZONTAL('-'),
    LEFT('/'), RIGHT('\\');

    companion object {
        val sings = values().map(Track::sign)
        fun parse(input: Char): Track {
            if (!sings.contains(input)) {
                throw IllegalArgumentException("$input")
            }
            return values().first { it.sign == input }
        }

        fun turn(track: Track, direction: Direction): Direction {
            return when (track) {
                INTERSECTION -> direction //cart direction should change separately
                VERTICAL -> direction
                HORIZONTAL -> direction
                LEFT -> when (direction) {

                    Direction.UP -> Direction.RIGHT
                    Direction.LEFT -> Direction.DOWN
                    Direction.DOWN -> Direction.LEFT
                    Direction.RIGHT -> Direction.UP
                }
                RIGHT -> when (direction) {
                    Direction.UP -> Direction.LEFT
                    Direction.LEFT -> Direction.UP
                    Direction.DOWN -> Direction.RIGHT
                    Direction.RIGHT -> Direction.DOWN
                }
            }

        }
    }

    fun turn(direction: Direction): Direction {
        return turn(this, direction)
    }

}

class Tracks(initial: List<String>) {
    private val carts: MutableList<Cart> = mutableListOf()
    private val input: MutableList<String> = initial.toMutableList()

    init {
        for ((y, line) in initial.withIndex()) {
            val chars = line.toCharArray()
            for ((x, char) in chars.withIndex()) {
                val direction = Direction.parse(char)
                direction?.let {
                    val element = Cart(x, y, it)
                    carts.add(element)
                    set(element.point, element.direction.toTrack().sign)
                }

            }
        }
    }

    fun get(p: Point): Char {
        return this.input[p.y][p.x]
    }

    fun set(p: Point, c: Char, target: MutableList<String> = input) {
        val s: String = target[p.y]
        target[p.y] = s.replaceRange(p.x, p.x + 1, "$c")
    }

    fun tick(): Boolean {
        carts.sort()
        carts.forEach {
            val point = it.next()
            val sign = get(point)
            val track = Track.parse(sign)

            it.move(point)
            it.turn(track)

            // did I crash to anybody
            if (carts.any { other -> other.onTrack && other.point == point && !other.equals(it) }) {
                println(point)
                //print()
                return false
            }
        }
        //print()
        return true
    }

    fun tick2(): Boolean {
        carts.sort()
        for (it in carts.filter { it.onTrack }){
            val point = it.next()


            // did I crash to anybody
            carts.filter { other -> other.onTrack &&  other.point == point }
                .forEach { crashed ->
                    crashed.onTrack = false
                    it.onTrack = false
                    println("${it.point} and ${crashed.point}")
                }

            val sign = get(point)
            val track = Track.parse(sign)
            it.turn(track)
            it.move(point)
        }

        val count = carts.filter(Cart::onTrack).count()
        if (count == 1) {
            println(carts.first(Cart::onTrack).point)
        }
        return count > 1
    }

    fun print(onTrack:Boolean = false) {
        val copy = input.toMutableList()
        if (onTrack)
            carts.filter(Cart::onTrack ).forEach {  set(it.point, it.direction.sign, copy) }
        else
            carts.forEach { set(it.point, it.direction.sign, copy) }
        return copy.forEach { println(it) }
    }
}
