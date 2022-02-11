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

fun currentDateTime(): DateTime {
    return Clock.System.now()
}

fun DateTime.getDay(): Int {
    return this.toDateTime().date.dayOfMonth
}

fun DateTime.getMonth(): Int {
    return this.toDateTime().date.monthNumber
}

fun DateTime.getYear(): Int {
    return this.toDateTime().date.year
}

fun Long?.toInstant(): Instant {
    return if (this == null) {
        Clock.System.now()
    } else {
        Instant.fromEpochMilliseconds(this)
    }
}