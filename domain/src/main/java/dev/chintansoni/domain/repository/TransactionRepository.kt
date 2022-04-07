package dev.chintansoni.domain.repository

import dev.chintansoni.domain.model.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {

    fun getAllTransactionsFlow(): Flow<List<Transaction>>

    fun getTransactionByIdFlow(id: Long): Flow<Transaction?>

    suspend fun addTransaction(transaction: Transaction): Long

    suspend fun updateTransaction(transaction: Transaction): Int

    suspend fun upsertTransaction(transaction: Transaction): Long

    suspend fun updateTransactions(transactions: List<Transaction>): List<Int>

    suspend fun deleteTransaction(transaction: Transaction): Int

    suspend fun getAllTransactionsByCategory(categoryId: Long): List<Transaction>

    suspend fun clear()
}
