package dev.chintansoni.domain.repository

import dev.chintansoni.domain.model.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {

    fun getAllTransactionsFlow(): Flow<List<Transaction>>

    fun getTransactionByIdFlow(id: Int): Flow<Transaction?>

    suspend fun addTransaction(transaction: Transaction): Long

    suspend fun updateTransaction(transaction: Transaction): Int

    suspend fun deleteTransaction(transaction: Transaction): Int

    suspend fun clear()
}
