package dev.chintansoni.common

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime

typealias DateTime = Instant

fun currentTimeZone() = TimeZone.currentSystemDefault()

fun DateTime.toDateTime(): LocalDateTime {
    return this.toLocalDateTime(currentTimeZone())
}

fun dateToDateTime(day: Int, month: Int, year: Int): DateTime {
    return LocalDateTime(
        year,
        month,
        day,
        0,
        0,
        0
    ).toInstant(currentTimeZone())
}

fun currentDateTime(): DateTime = Clock.System.now()

fun currentDateTimeInMillis(): Long = currentDateTime().toEpochMilliseconds()

fun DateTime.getDay(): Int = this.toDateTime().date.dayOfMonth

fun DateTime.getMonth(): Int = this.toDateTime().date.monthNumber

fun DateTime.getYear(): Int = this.toDateTime().date.year

fun Long?.toInstant(): DateTime = if (this == null) {
    currentDateTime()
} else {
    DateTime.fromEpochMilliseconds(this)
}