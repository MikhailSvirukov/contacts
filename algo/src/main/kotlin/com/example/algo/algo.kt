package com.example.algo

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.ZoneId
import java.time.temporal.TemporalAdjusters

enum class WeekDay(val range: IntRange) {
    MONDAY(0..6),
    TUESDAY(-1..5),
    WEDNESDAY(-2..4),
    THURSDAY(-3..3),
    FRIDAY(-4..2),
    SATURDAY(-5..1),
    SUNDAY(-6..0);
}

fun currentWeekDates(
    firstDay: DayOfWeek = DayOfWeek.MONDAY,
    zone: ZoneId = ZoneId.systemDefault()
): List<LocalDate> {
    val today = LocalDate.now(zone)
    val start = today.with(TemporalAdjusters.previousOrSame(firstDay))
    return (0..6).map { start.plusDays(it.toLong()) }
}

fun selection(employees: Employees): WeeklyResult {
    val range = when (LocalDate.now().dayOfWeek) {
        DayOfWeek.MONDAY -> WeekDay.MONDAY.range
        DayOfWeek.TUESDAY -> WeekDay.TUESDAY.range
        DayOfWeek.WEDNESDAY -> WeekDay.WEDNESDAY.range
        DayOfWeek.THURSDAY -> WeekDay.THURSDAY.range
        DayOfWeek.FRIDAY -> WeekDay.FRIDAY.range
        DayOfWeek.SATURDAY -> WeekDay.SATURDAY.range
        DayOfWeek.SUNDAY -> WeekDay.SUNDAY.range
    }

    val currentWeek = currentWeekDates().associateWith { WeeklyHeroes() }.toMutableMap()

    employees
        .mapNotNull { emp ->
            val low = (emp.workDays / PERIOD) * PERIOD
            val up = low + PERIOD

            val anniversary = when {
                low > 0 && low in (emp.workDays + range.first..emp.workDays + range.last) -> low
                up in (emp.workDays + range.first..emp.workDays + range.last) -> up
                else -> return@mapNotNull null
            }

            val date = LocalDate.now().plusDays((anniversary - emp.workDays).toLong())

            Hero(
                emp.name,
                date,
                anniversary
            )
        }
        .forEach { currentWeek[it.date]?.heroes?.add("${it.name} - ${it.days} дней") }

    return WeeklyResult(currentWeek)
}