package com.advent.ten

import java.util.regex.Pattern

/**
 * Created on 2019-02-04.
 */
data class Point(var x: Int, var y: Int, val velX: Int, val velY: Int) {
    fun tick() {
        this.x += this.velX
        this.y += this.velY
    }
}

class Manager(val instructions: List<String>) {

    val points: MutableList<Point> = mutableListOf()

    companion object {
        private val PATTERN = Pattern.compile("position=<([^,]+),([^>]+)> velocity=<([^,]*),([^>]*)>")

        fun parse(input: String): Point {
            val p = PATTERN.matcher(input)
            if (!p.find()) {
                throw  RuntimeException("${input} cannot be parsed")
            }
            val parsedX = p.group(1).strip().toInt()
            val parsedY = p.group(2).strip().toInt()
            val parsedVelX = p.group(3).strip().toInt()
            val parsedVelY = p.group(4).strip().toInt()

            return Point(parsedX, parsedY, parsedVelX, parsedVelY)
        }

    }

    init {
        for (instruction in instructions) {
            points.add(parse(instruction))
        }
    }

    fun tick() {
        points.forEach(Point::tick)
    }

    fun print() {
        val maxX = points.stream().mapToInt(Point::x).max().asInt
        val minX = points.stream().mapToInt(Point::x).min().asInt

        val maxY = points.stream().mapToInt(Point::y).max().asInt
        val minY = points.stream().mapToInt(Point::y).min().asInt

        val map: MutableList<CharArray> = mutableListOf()


        val rangeX = maxX - minX + 1
        val rangeY = maxY - minY + 1

        IntRange(0, rangeY).forEach {
            val charArray = CharArray(rangeX)
            charArray.fill('.')
            map.add(charArray)
        }

        for (p in points) {
            map[Math.abs(minY) + p.y][Math.abs(minX) + p.x] = '#' // needs adjust according to minx minY
        }

        for (ca in map) {
            for (c in ca) {
                print(c)
            }
            println("")
        }


    }
}
