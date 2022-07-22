package dev.chintansoni.repository.impl

import dev.chintansoni.database.entity.transaction.TransactionDao
import dev.chintansoni.domain.model.Transaction
import dev.chintansoni.domain.repository.TransactionRepository
import dev.chintansoni.repository.mapper.toDBModel
import dev.chintansoni.repository.mapper.toDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

internal class TransactionRepositoryImpl(
    private val transactionDao: TransactionDao
) : TransactionRepository {

    override fun getAllTransactionsFlow(): Flow<List<Transaction>> =
        transactionDao.getAllFlow().distinctUntilChanged().map { list ->
            list.map { transactionDetailView ->
                transactionDetailView.toDomainModel()
            }
        }

    override fun getTransactionByIdFlow(id: Long): Flow<Transaction?> =
        transactionDao.getByIdFlow(id).distinctUntilChanged().map { transactionDetailView ->
            transactionDetailView?.toDomainModel()
        }

    override suspend fun addTransaction(transaction: Transaction): Long {
        return transactionDao.insertTransaction(transaction.toDBModel())
    }

    override suspend fun updateTransaction(transaction: Transaction): Int {
        return transactionDao.updateTransaction(transaction.toDBModel())
    }

    override suspend fun upsertTransaction(transaction: Transaction): Long {
        return transactionDao.upsertTransaction(transaction.toDBModel())
    }

    override suspend fun updateTransactions(transactions: List<Transaction>): List<Int> {
        return transactionDao.updateTransactions(transactions.map { it.toDBModel() })
    }

    override suspend fun deleteTransaction(transaction: Transaction): Int {
        return transactionDao.deleteTransaction(transaction.toDBModel())
    }

    override suspend fun getAllTransactionsByCategory(categoryId: Long): List<Transaction> {
        return transactionDao.getAllByCategory(categoryId).map { it.toDomainModel() }
    }

    override suspend fun clear() {
        return transactionDao.clearTable()
    }
}