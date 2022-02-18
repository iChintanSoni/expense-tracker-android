package dev.chintansoni.expensetracker.ui.transactiondetail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.chintansoni.common.DateTime
import dev.chintansoni.domain.model.Transaction
import dev.chintansoni.domain.repository.TransactionRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TransactionDetailViewModel(
    private val transactionId: Long,
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        uiError.value = if (throwable is TransactionDetailException) {
            throwable
        } else {
            UnknownException(throwable)
        }
    }

    var transaction = mutableStateOf(Transaction.newInstance())
        private set
    var amountError = mutableStateOf("")
        private set
    var uiError = mutableStateOf<Exception>(NoException)
        private set

    init {
        viewModelScope.launch(exceptionHandler) {
            if (transactionId != 0L) {
                transactionRepository
                    .getTransactionByIdFlow(transactionId)
                    .collectLatest { transaction ->
                        transaction?.let {
                            this@TransactionDetailViewModel.transaction.value = it
                        } ?: throw NotFoundException(transactionId)
                    }
            }
        }
    }

    fun setAmount(amount: String) {
        amountError.value = try {
            transaction.value = transaction.value.copy(amount = amount.toFloat())
            ""
        } catch (exception: Exception) {
            "Please enter a valid amount"
        }
    }

    fun setNote(note: String) {
        transaction.value = transaction.value.copy(note = note)
    }

    fun setCategory(categoryId: Int) {
        transaction.value = transaction.value.copy(category = categoryId)
    }

    suspend fun addUpdateExpense(transaction: Transaction) {
        if (transaction.id == 0L) {
            transactionRepository.addTransaction(transaction)
        } else {
            transactionRepository.updateTransaction(transaction)
        }
    }

    suspend fun deleteExpense(transaction: Transaction) {
        transactionRepository.deleteTransaction(transaction)
    }

    fun setDate(date: DateTime) {
        transaction.value = transaction.value.copy(date = date.toEpochMilliseconds())
    }
}