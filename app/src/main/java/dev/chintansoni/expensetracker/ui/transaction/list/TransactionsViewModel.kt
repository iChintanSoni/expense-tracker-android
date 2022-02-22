package dev.chintansoni.expensetracker.ui.transaction.list

import androidx.lifecycle.ViewModel
import dev.chintansoni.domain.model.Transaction
import dev.chintansoni.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow

class TransactionsViewModel(transactionRepository: TransactionRepository) : ViewModel() {

    val transactionsFlow: Flow<List<Transaction>> = transactionRepository.getAllTransactionsFlow()
}
