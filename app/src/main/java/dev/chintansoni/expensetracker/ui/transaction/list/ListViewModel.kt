package dev.chintansoni.expensetracker.ui.transaction.list

import dev.chintansoni.domain.repository.TransactionDetailRepository
import dev.chintansoni.expensetracker.base.BaseViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlin.coroutines.CoroutineContext

class ListViewModel(private val transactionDetailRepository: TransactionDetailRepository) :
    BaseViewModel<ListViewContract.Event, ListViewContract.State, ListViewContract.Effect>() {

    init {
        setEvent(ListViewContract.Event.FetchTransactions)
    }

    override fun createInitialState(): ListViewContract.State = ListViewContract.State.default()

    override fun handleEvent(event: ListViewContract.Event) {
        when (event) {
            ListViewContract.Event.FetchTransactions -> fetchAllTransactions()
            is ListViewContract.Event.OnTransactionClick -> handleTransactionClick(event.transactionId)
        }
    }

    private fun handleTransactionClick(transactionId: Long) {
        setEffect {
            ListViewContract.Effect.NavigateToDetail(transactionId = transactionId)
        }
    }

    private fun fetchAllTransactions() {
        launchInIO {
            transactionDetailRepository.getAllTransactionsFlow().collectLatest {
                setState { copy(transactionDetailList = it) }
            }
        }
    }


    override fun handleException(coroutineContext: CoroutineContext, throwable: Throwable) {

    }
}
