package dev.chintansoni.expensetracker.ui.util

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

val currentTimeZone = TimeZone.currentSystemDefault()

fun Instant.toDateTime(): LocalDateTime {
    return this.toLocalDateTime(currentTimeZone)
}

fun toInstant(day: Int, month: Int, year: Int): Instant {
    return LocalDateTime(
        year,
        month,
        day,
        0,
        0,
        0
    ).toInstant(currentTimeZone)
}

fun currentInstant(): Instant {
    return Clock.System.now()
}

fun Instant.getDay(): Int {
    return this.toDateTime().date.dayOfMonth
}

fun Instant.getMonth(): Int {
    return this.toDateTime().date.monthNumber
}

fun Instant.getYear(): Int {
    return this.toDateTime().date.year
}