package com.rtarita.days

import com.rtarita.structure.AoCDay
import com.rtarita.util.day
import kotlinx.datetime.LocalDate
import kotlin.math.abs

object Day1 : AoCDay {
    override val day: LocalDate = day(1)

    private val SEPARATOR_REGEX = Regex("\\s+")

    private fun parseLocations(input: String): Pair<List<Int>, List<Int>> = input.lineSequence()
        .map { it.split(SEPARATOR_REGEX) }
        .map { (left, right) -> left.toInt() to right.toInt() }
        .unzip()

    override fun executePart1(input: String): Any {
        val (left, right) = parseLocations(input)
        return (left.sorted() zip right.sorted()).sumOf { (a, b) -> abs(a - b) }
    }

    override fun executePart2(input: String): Any {
        val (left, right) = parseLocations(input)
        val occurrences = right.groupingBy { it }
            .eachCount()
        return left.sumOf { it * (occurrences[it] ?: 0) }
    }
}