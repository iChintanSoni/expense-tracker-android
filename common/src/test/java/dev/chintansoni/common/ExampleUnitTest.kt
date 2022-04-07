package dev.chintansoni.common

import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun checkIfDatesAreCorrect() {
        println("Start of Year: ${getStartDateOfCurrentYear().toPrintableDate()}")
        println("End of Year: ${getEndDateOfCurrentYear().toPrintableDate()}")
        println("Start of Month: ${getStartDateOfCurrentMonth().toPrintableDate()}")
        println("End of Month: ${getEndDateOfCurrentMonth().toPrintableDate()}")
        println("Start of Week: ${getStartDateOfCurrentWeek().toPrintableDate()}")
        println("End of Week: ${getEndDateOfCurrentWeek().toPrintableDate()}")
        println("Start of Day: ${getStartDateOfCurrentDay().toPrintableDate()}")
        println("End of Day: ${getEndDateOfCurrentDay().toPrintableDate()}")
    }
}