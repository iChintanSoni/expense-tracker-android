package dev.chintansoni.common

import kotlinx.datetime.*
import kotlinx.datetime.TimeZone
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

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

fun Long?.toDateTime(): DateTime = if (this == null) {
    currentDateTime()
} else {
    DateTime.fromEpochMilliseconds(this)
}

fun Long?.toPrintableDate(): String {
    val simpleDateFormat = SimpleDateFormat.getDateInstance(DateFormat.LONG)
    val date = Date(this ?: currentDateTimeInMillis())
    return simpleDateFormat.format(date)
}