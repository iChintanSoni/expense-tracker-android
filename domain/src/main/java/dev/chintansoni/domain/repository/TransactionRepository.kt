package dev.chintansoni.domain.repository

import dev.chintansoni.domain.model.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {

    fun getAllTransactionsFlow(): Flow<List<Transaction>>

    fun getTransactionByIdFlow(id: Int): Flow<Transaction?>

    suspend fun addTransaction(transaction: Transaction)

    suspend fun updateTransaction(transaction: Transaction)

    suspend fun deleteTransaction(transaction: Transaction)

    suspend fun clear()
}
