package dev.chintansoni.expensetracker.ui.util

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun Instant.toDateTime(): LocalDateTime {
    return this.toLocalDateTime(TimeZone.currentSystemDefault())
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