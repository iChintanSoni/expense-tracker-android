package dev.chintansoni.expensetracker.ui.home.analytics

import dev.chintansoni.common.getEndDateOfCurrentDay
import dev.chintansoni.common.getEndDateOfCurrentMonth
import dev.chintansoni.common.getEndDateOfCurrentWeek
import dev.chintansoni.common.getEndDateOfCurrentYear
import dev.chintansoni.common.getStartDateOfCurrentDay
import dev.chintansoni.common.getStartDateOfCurrentMonth
import dev.chintansoni.common.getStartDateOfCurrentWeek
import dev.chintansoni.common.getStartDateOfCurrentYear
import dev.chintansoni.domain.model.TransactionDetail
import dev.chintansoni.domain.repository.TransactionDetailRepository
import dev.chintansoni.expensetracker.base.BaseViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlin.coroutines.CoroutineContext

class AnalyticsViewModel(private val transactionDetailRepository: TransactionDetailRepository) :
    BaseViewModel<AnalyticsViewContract.Event, AnalyticsViewContract.State, AnalyticsViewContract.Effect>() {

    init {
        setEvent(AnalyticsViewContract.Event.OnDateFilterSelected(DateFilter.ThisMonth()))
    }

    override fun createInitialState(): AnalyticsViewContract.State =
        AnalyticsViewContract.State.default()

    override fun handleEvent(event: AnalyticsViewContract.Event) {
        when (event) {
            is AnalyticsViewContract.Event.OnDateFilterSelected -> handleDateFilterChange(event.dateFilter)
        }
    }

    private fun handleDateFilterChange(dateFilter: DateFilter) {
        setState { copy(selectedDateFilter = dateFilter) }
        val (startDate, endDate) = when (dateFilter) {
            is DateFilter.ThisMonth -> getStartDateOfCurrentMonth() to getEndDateOfCurrentMonth()
            is DateFilter.ThisWeek -> getStartDateOfCurrentWeek() to getEndDateOfCurrentWeek()
            is DateFilter.ThisYear -> getStartDateOfCurrentYear() to getEndDateOfCurrentYear()
            is DateFilter.Today -> getStartDateOfCurrentDay() to getEndDateOfCurrentDay()
        }
        launchInIO {
            transactionDetailRepository.getBetweenDates(startDate = startDate, endDate = endDate)
                .collectLatest { transactionDetailList ->
                    val groupedTransactionDetail: Map<String, List<TransactionDetail>> =
                        transactionDetailList.groupBy { transactionDetail -> transactionDetail.categoryName }
                    val list: List<Pair<String, Double>> =
                        groupedTransactionDetail.map { entry: Map.Entry<String, List<TransactionDetail>> ->
                            entry.key to entry.value.sumOf { it.amount.toDouble() }
                        }
                    setState {
                        copy(
                            categoryTotalList = list,
                            totalSpent = transactionDetailList.sumOf { it.amount.toDouble() }
                        )
                    }
                }
        }
    }

    override fun handleException(coroutineContext: CoroutineContext, throwable: Throwable) {

    }
}