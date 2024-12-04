package com.rtarita.days

import com.rtarita.structure.AoCDay
import com.rtarita.util.day
import kotlinx.datetime.LocalDate

object Day3 : AoCDay {
    override val day: LocalDate = day(3)

    private val MUL_REGEX = Regex("mul\\(([0-9]{1,3}),([0-9]{1,3})\\)")
    private val MUL_OR_INSTRUCTION_REGEX = Regex("(?:${MUL_REGEX.pattern}|do\\(\\)|don't\\(\\))")

    private fun multiplyMatch(match: MatchResult): Int {
        val (a, b) = match.destructured
        return a.toInt() * b.toInt()
    }

    override fun executePart1(input: String): Any {
        return MUL_REGEX.findAll(input)
            .sumOf { multiplyMatch(it) }
    }

    override fun executePart2(input: String): Any {
        return MUL_OR_INSTRUCTION_REGEX.findAll(input).fold(0 to true) { (sum, enabled), match ->
            when (match.value) {
                "do()" -> sum to true
                "don't()" -> sum to false
                else -> (sum + if (enabled) multiplyMatch(match) else 0) to enabled
            }
        }.first
    }
}