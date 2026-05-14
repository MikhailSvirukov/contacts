package com.example.algo

import java.time.LocalDate

const val PERIOD: Int = 1000
const val WEEKDAYS: Int = 7

data class Hero(
    val name: String,
    val date: LocalDate,
    val days: Int,
    val period: Int = PERIOD,
) {
    init {
        assert(days % PERIOD == 0)
    }
}

data class WeeklyHeroes(
    var heroes: MutableList<String> = mutableListOf(),
)

data class WeeklyResult(
    val inner: MutableMap<LocalDate, WeeklyHeroes>,
) {
    init {
        assert(inner.size == WEEKDAYS)
    }

    override fun toString(): String =
        inner.entries.joinToString(separator = "\n") { (date, heroes) ->
            "$date: ${heroes.heroes.joinToString(separator = "\n")}"
        }
}
