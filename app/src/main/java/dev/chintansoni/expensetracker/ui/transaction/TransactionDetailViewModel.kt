package dev.chintansoni.expensetracker.ui.transaction

import androidx.lifecycle.ViewModel
import dev.chintansoni.domain.model.Transaction
import dev.chintansoni.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TransactionDetailViewModel(private val transactionRepository: TransactionRepository) :
    ViewModel() {

    fun getTransactionFlow(id: Int): Flow<Transaction?> {
        return if (id == 0) {
            flow {
                emit(Transaction.newInstance())
            }
        } else {
            transactionRepository.getTransactionByIdFlow(id)
        }
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
}