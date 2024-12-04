package com.rtarita.days

import com.rtarita.structure.AoCDay
import com.rtarita.util.day
import com.rtarita.util.repeat
import kotlinx.datetime.LocalDate

object Day4 : AoCDay {
    override val day: LocalDate = day(4)

    private const val IGNORE_CHAR = '.'
    private fun List<List<Char>>.getSafe(x: Int, y: Int): Char {
        if (y >= size) return IGNORE_CHAR
        val row = this[y]
        if (x >= row.size) return IGNORE_CHAR
        return row[x]
    }

    fun List<List<Char>>.match(kernel: List<List<Char>>, startX: Int, startY: Int): Boolean {
        for ((y, row) in kernel.withIndex()) {
            for ((x, c) in row.withIndex()) {
                if (c == IGNORE_CHAR) continue
                if (c != getSafe(startX + x, startY + y)) return false
            }
        }
        return true
    }

    private val XMAS = listOf('X', 'M', 'A', 'S')
    private val KERNEL_H_POS = listOf(XMAS)
    private val KERNEL_H_NEG = listOf(XMAS.reversed())
    private val KERNEL_V_POS = XMAS.map { listOf(it) }
    private val KERNEL_V_NEG = XMAS.reversed().map { listOf(it) }
    private val KERNEL_D1_POS = XMAS.mapIndexed { idx, c -> IGNORE_CHAR.repeat(idx).toList() + c }
    private val KERNEL_D1_NEG = XMAS.reversed().mapIndexed { idx, c -> IGNORE_CHAR.repeat(idx).toList() + c }
    private val KERNEL_D2_POS = XMAS.mapIndexed { idx, c -> IGNORE_CHAR.repeat(XMAS.size - idx - 1).toList() + c }
    private val KERNEL_D2_NEG = XMAS.reversed().mapIndexed { idx, c -> IGNORE_CHAR.repeat(XMAS.size - idx - 1).toList() + c }
    private val KERNELS = listOf(
        KERNEL_H_POS, KERNEL_H_NEG,
        KERNEL_V_POS, KERNEL_V_NEG,
        KERNEL_D1_POS, KERNEL_D1_NEG,
        KERNEL_D2_POS, KERNEL_D2_NEG
    )

    private val KERNEL_MAS_1 = listOf(listOf('M', '.', 'M'), listOf('.', 'A', '.'), listOf('S', '.', 'S'))
    private val KERNEL_MAS_2 = KERNEL_MAS_1.reversed()
    private val KERNEL_MAS_3 = listOf(listOf('M', '.', 'S'), listOf('.', 'A', '.'), listOf('M', '.', 'S'))
    private val KERNEL_MAS_4 = KERNEL_MAS_3.map { it.reversed() }
    private val KERNELS_MAS = listOf(
        KERNEL_MAS_1,
        KERNEL_MAS_2,
        KERNEL_MAS_3,
        KERNEL_MAS_4
    )

    private fun parseGrid(input: String): List<List<Char>> = input.lines()
        .map { it.toList() }

    private fun countMatches(grid: List<List<Char>>, kernels: List<List<List<Char>>>) = grid.mapIndexed { y, row ->
        row.indices.sumOf { x ->
            kernels.count { grid.match(it, x, y) }
        }
    }.sum()

    override fun executePart1(input: String): Any {
        return countMatches(parseGrid(input), KERNELS)
    }

    override fun executePart2(input: String): Any {
        return countMatches(parseGrid(input), KERNELS_MAS)
    }
}