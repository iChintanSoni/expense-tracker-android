package dev.chintansoni.common

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

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

fun DateTime.getDay(): Int = toDateTime().date.dayOfMonth

fun DateTime.getMonth(): Int = toDateTime().date.monthNumber

fun DateTime.getYear(): Int = toDateTime().date.year

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

fun getStartDateOfCurrentYear(): Long {
    return Calendar.getInstance().apply {
        set(
            Calendar.DAY_OF_YEAR,
            getActualMinimum(Calendar.DAY_OF_YEAR)
        )
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }.timeInMillis
}

fun getEndDateOfCurrentYear(): Long {
    return Calendar.getInstance().apply {
        set(
            Calendar.DAY_OF_YEAR,
            getActualMaximum(Calendar.DAY_OF_YEAR)
        )
        set(Calendar.HOUR_OF_DAY, 23)
        set(Calendar.MINUTE, 59)
        set(Calendar.SECOND, 59)
        set(Calendar.MILLISECOND, 999)
    }.timeInMillis
}

fun getStartDateOfCurrentMonth(): Long {
    return Calendar.getInstance().apply {
        set(
            Calendar.DAY_OF_MONTH,
            getActualMinimum(Calendar.DAY_OF_MONTH)
        )
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }.timeInMillis
}

fun getEndDateOfCurrentMonth(): Long {
    return Calendar.getInstance().apply {
        set(
            Calendar.DAY_OF_MONTH,
            getActualMaximum(Calendar.DAY_OF_MONTH)
        )
        set(Calendar.HOUR_OF_DAY, 23)
        set(Calendar.MINUTE, 59)
        set(Calendar.SECOND, 59)
        set(Calendar.MILLISECOND, 999)
    }.timeInMillis
}

fun getStartDateOfCurrentWeek(): Long {
    return Calendar.getInstance().apply {
        set(
            Calendar.DAY_OF_WEEK,
            getActualMinimum(Calendar.DAY_OF_WEEK)
        )
        set(Calendar.HOUR_OF_DAY, 0)
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }.timeInMillis
}

fun getEndDateOfCurrentWeek(): Long {
    return Calendar.getInstance().apply {
        set(
            Calendar.DAY_OF_WEEK,
            getActualMaximum(Calendar.DAY_OF_WEEK)
        )
        set(Calendar.HOUR_OF_DAY, 23)
        set(Calendar.MINUTE, 59)
        set(Calendar.SECOND, 59)
        set(Calendar.MILLISECOND, 999)
    }.timeInMillis
}

fun getStartDateOfCurrentDay(): Long {
    return Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, getActualMinimum(Calendar.HOUR_OF_DAY))
        set(Calendar.MINUTE, 0)
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
    }.timeInMillis
}

fun getEndDateOfCurrentDay(): Long {
    return Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, getActualMaximum(Calendar.HOUR_OF_DAY))
        set(Calendar.MINUTE, 59)
        set(Calendar.SECOND, 59)
        set(Calendar.MILLISECOND, 999)
    }.timeInMillis
}
