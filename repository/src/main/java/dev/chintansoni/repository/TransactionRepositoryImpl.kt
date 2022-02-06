package dev.chintansoni.repository

import dev.chintansoni.database.entity.transaction.TransactionDao
import dev.chintansoni.domain.model.Transaction
import dev.chintansoni.domain.repository.TransactionRepository
import dev.chintansoni.repository.mapper.toDBModel
import dev.chintansoni.repository.mapper.toDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class TransactionRepositoryImpl(private val transactionDao: TransactionDao) :
    TransactionRepository {

    override fun getAllTransactionsFlow(): Flow<List<Transaction>> =
        transactionDao.getAllFlow().distinctUntilChanged().map { list ->
            list.map { transactionEntity ->
                transactionEntity.toDomainModel()
            }
        }

    override fun getTransactionByIdFlow(id: Int): Flow<Transaction?> =
        transactionDao.getByIdFlow(id).distinctUntilChanged().map { transactionEntity ->
            transactionEntity?.toDomainModel()
        }

    override suspend fun addTransaction(transaction: Transaction): Long {
        return transactionDao.insertTransaction(transaction.toDBModel())
    }

    override suspend fun updateTransaction(transaction: Transaction): Int {
        return transactionDao.updateTransaction(transaction.toDBModel())
    }

    override suspend fun deleteTransaction(transaction: Transaction): Int {
        return transactionDao.deleteTransaction(transaction.toDBModel())
    }

    override suspend fun clear() {
        return transactionDao.clearTable()
    }
}