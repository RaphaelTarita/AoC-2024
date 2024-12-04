package com.rtarita.days

import com.rtarita.structure.AoCDay
import com.rtarita.util.day
import kotlinx.datetime.LocalDate

object Day2 : AoCDay {
    override val day: LocalDate = day(2)

    private val DECREASE_RANGE = -3..-1
    private val INCREASE_RANGE = 1..3

    private fun parseReports(input: String) = input.lines()
        .map { it.split(" ") }
        .map { report -> report.map { it.toInt() } }

    private fun checkDeltas(levels: List<Int>): Boolean {
        val deltas = levels.zipWithNext { a, b -> b - a }
        return deltas.all { it in DECREASE_RANGE } || deltas.all { it in INCREASE_RANGE }
    }

    override fun executePart1(input: String): Any {
        return parseReports(input).count { checkDeltas(it) }
    }

    override fun executePart2(input: String): Any {
        return parseReports(input).count { levels ->
            checkDeltas(levels) || levels.indices.any { lvlIdx ->
                checkDeltas(levels.filterIndexed { idx, _ -> idx != lvlIdx })
            }
        }
    }
}