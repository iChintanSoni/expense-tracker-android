package dev.chintansoni.expensetracker.ui.transaction

import androidx.lifecycle.ViewModel
import dev.chintansoni.domain.model.Transaction
import dev.chintansoni.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow

class TransactionDetailViewModel(private val transactionRepository: TransactionRepository) :
    ViewModel() {

    fun getTransactionFlow(id: Int): Flow<Transaction?> {
        return transactionRepository.getTransactionByIdFlow(id)
    }

    suspend fun addUpdateExpense(transaction: Transaction) {
        if (transaction.id == 0) {
            transactionRepository.addTransaction(transaction)
        } else {
            transactionRepository.updateTransaction(transaction)
        }
    }

    suspend fun deleteExpense(transaction: Transaction) {
        transactionRepository.deleteTransaction(transaction)
    }
}