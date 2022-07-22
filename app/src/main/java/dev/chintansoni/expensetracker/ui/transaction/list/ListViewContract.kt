package dev.chintansoni.expensetracker.ui.transaction.list

import dev.chintansoni.domain.model.TransactionDetail
import dev.chintansoni.expensetracker.base.UiEffect
import dev.chintansoni.expensetracker.base.UiEvent
import dev.chintansoni.expensetracker.base.UiState

class ListViewContract {

    sealed class Event : UiEvent {
        object FetchTransactions : Event()
        data class OnTransactionClick(val transactionId: Long) : Event()
    }

    data class State(val transactionDetailList: List<TransactionDetail>) : UiState {
        companion object {
            fun default() = State(transactionDetailList = emptyList())

            fun dummy() = State(transactionDetailList = TransactionDetail.dummyList())
        }
    }

    sealed class Effect : UiEffect {
        object Nothing : Effect()
        data class NavigateToDetail(val transactionId: Long) : Effect()
    }
}
