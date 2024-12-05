package com.rtarita.days

import com.rtarita.structure.AoCDay
import com.rtarita.util.day
import kotlinx.datetime.LocalDate
import kotlin.math.max

object Day5 : AoCDay {
    override val day: LocalDate = day(5)

    private fun parseInput(input: String): Pair<Map<Int, Set<Int>>, List<List<Int>>> {
        val (rulesString, updatesString) = input.split("\n\n")
        val rules = rulesString.lines()
            .map {
                val (a, b) = it.split('|')
                a.toInt() to b.toInt()
            }
            .groupingBy { it.first }
            .fold(setOf<Int>()) { acc, elem -> acc + elem.second }

        val updates = updatesString.lines()
            .map { update -> update.split(',').map { it.toInt() } }
        return rules to updates
    }

    private fun List<Int>.middle() = this[size / 2]

    override fun executePart1(input: String): Any {
        val (rules, updates) = parseInput(input)
        return updates.filter { update ->
            val seen = hashSetOf<Int>()
            for (page in update) {
                seen += page
                val laterPages = rules[page] ?: continue
                if (laterPages.any { it in seen }) return@filter false
            }
            true
        }.sumOf { it.middle() }
    }

    override fun executePart2(input: String): Any {
        val (rules, updates) = parseInput(input)
        val fixedUpdates = mutableListOf<List<Int>>()
        for (originalUpdate in updates) {
            val update = originalUpdate.toMutableList()
            var modified = false
            var idx = 0
            while (idx < update.size) {
                val pageIdx = idx++
                val page = update[pageIdx]
                val subsequent = rules[page] ?: continue
                val swapIdx = update.subList(0, idx)
                    .indexOfFirst { it in subsequent }
                    .takeIf { it > -1 }
                    ?: continue
                update[pageIdx] = update[swapIdx]
                update[swapIdx] = page
                modified = true
                idx = max(0, swapIdx - 1)
            }
            if (modified) fixedUpdates += update
        }
        return fixedUpdates.sumOf { it.middle() }
    }
}