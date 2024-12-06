package com.rtarita.days

import com.rtarita.days.Day6.Direction.entries
import com.rtarita.structure.AoCDay
import com.rtarita.util.day
import com.rtarita.util.emptyEnumSet
import kotlinx.datetime.LocalDate

object Day6 : AoCDay {
    override val day: LocalDate = day(6)

    private enum class Direction(val deltaX: Int, val deltaY: Int) {
        UP(0, -1),
        RIGHT(1, 0),
        DOWN(0, 1),
        LEFT(-1, 0);

        fun rotate() = entries[(ordinal + 1) % entries.size]
    }

    private data class Coord(val x: Int, val y: Int) {
        fun move(direction: Direction) = Coord(x + direction.deltaX, y + direction.deltaY)
    }

    private fun Pair<Int, Int>.swap() = second to first
    private fun Pair<Int, Int>.toCoord() = Coord(first, second)

    private fun parseInput(input: String): Pair<List<List<Boolean>>, Coord> {
        val map = input.lines()
            .map { line -> line.map { it == '#' } }
        val guardPos = input.lines().mapIndexed { idx, line ->
            idx to line.indexOfFirst { it == '^' }
        }
            .first { it.second != -1 }
            .swap()
            .toCoord()
        return map to guardPos
    }

    private fun findVisited(map: List<List<Boolean>>, initial: Coord): Set<Coord>? {
        val yRange = 0..<map.size
        val xRange = 0..<map[0].size
        val visited = HashMap<Coord, MutableSet<Direction>>(map.size * map[0].size)
        var current = initial
        var currentDirection = Direction.UP
        while (current.x in xRange && current.y in yRange) {
            val directions = visited.getOrPut(current) { emptyEnumSet<Direction>() }
            if (currentDirection in directions) return null
            directions += currentDirection
            val next = current.move(currentDirection)
            if (next.x in xRange && next.y in yRange && map[next.y][next.x]) {
                currentDirection = currentDirection.rotate()
            } else {
                current = next
            }
        }
        return visited.keys
    }

    override fun executePart1(input: String): Any {
        val (map, guardPos) = parseInput(input)
        return findVisited(map, guardPos)?.size ?: error("guard stuck in a loop")
    }

    override fun executePart2(input: String): Any {
        val (map, guardPos) = parseInput(input)
        val visited = findVisited(map, guardPos) ?: error("guard stuck in a loop in initial map")
        return visited.count { coord ->
            val newMap = map.map { it.toMutableList() }.toMutableList()
            newMap[coord.y][coord.x] = true
            findVisited(newMap, guardPos) == null
        }
    }
}