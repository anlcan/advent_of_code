package com.advent.ten


import java.awt.Color
import java.awt.image.BufferedImage
import java.awt.image.BufferedImage.TYPE_INT_RGB
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.regex.Pattern
import javax.imageio.ImageIO


/**
 * Created on 2019-02-04.
 */
data class Point(var x: Int, var y: Int, val velX: Int, val velY: Int) {
    fun tick() {
        this.x += this.velX
        this.y += this.velY
    }

    fun key(): String {
        return "$x,$y"
    }
}

class Manager(instructions: List<String>) {

    val points: MutableList<Point> = mutableListOf()
    var coords: MutableList<String> = mutableListOf()

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
            val parsed = parse(instruction)
            points.add(parsed)
            coords.add(parsed.key())
        }
    }

    fun tick() {

        coords.clear()
        points.forEach {
            it.tick()
            coords.add(it.key())
        }
    }

    fun print() {
        print(false)
    }

    fun print(log2file: Boolean, fileName: String? = "") {
        val maxX = points.stream().mapToInt(Point::x).max().asInt
        val minX = points.stream().mapToInt(Point::x).min().asInt

        val maxY = points.stream().mapToInt(Point::y).max().asInt
        val minY = points.stream().mapToInt(Point::y).min().asInt

        val rangeX = maxX - minX + 1
        val rangeY = maxY - minY + 1


        if (rangeX > 300 || rangeY > 300) {
            return
        }
        println("$rangeX $rangeY")
        val white = Color.white

        val tmpDir: Path = Paths.get(this.javaClass.getResource("/ten").toURI())
        val tmpfile = Files.createTempFile(tmpDir, fileName, ".png").toFile()


        val bufferedImage = BufferedImage(300, 300, TYPE_INT_RGB)
        points.forEach {
            bufferedImage.setRGB((-1 * minX) + it.x, (-1 * minY) + it.y, white.rgb)
        }
        bufferedImage.flush()
        ImageIO.write(bufferedImage, "png", tmpfile)

    }
}
