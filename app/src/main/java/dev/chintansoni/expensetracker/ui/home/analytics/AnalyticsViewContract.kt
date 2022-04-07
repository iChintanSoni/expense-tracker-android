package dev.chintansoni.expensetracker.ui.home.analytics

import dev.chintansoni.expensetracker.base.UiEffect
import dev.chintansoni.expensetracker.base.UiEvent
import dev.chintansoni.expensetracker.base.UiState

class AnalyticsViewContract {

    sealed class Event : UiEvent {
        data class OnDateFilterSelected(val dateFilter: DateFilter) : Event()
    }

    data class State(
        val selectedDateFilter: DateFilter,
        val totalSpent: Double,
        val categoryTotalList: List<Pair<String, Double>>
    ) : UiState {
        companion object {
            fun default() =
                State(
                    selectedDateFilter = DateFilter.ThisMonth(),
                    categoryTotalList = emptyList(),
                    totalSpent = 0.0
                )
        }
    }

    sealed class Effect : UiEffect {
        object Nothing : Effect()
    }
}

