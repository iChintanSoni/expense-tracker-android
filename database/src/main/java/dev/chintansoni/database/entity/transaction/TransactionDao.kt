package dev.chintansoni.database.entity.transaction

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Query("SELECT * FROM TransactionEntity")
    fun getAllFlow(): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM TransactionEntity WHERE id = :id LIMIT 1")
    fun getByIdFlow(id: Long): Flow<TransactionEntity?>

    @Query("SELECT * FROM TransactionEntity WHERE category = :categoryId")
    suspend fun getAllByCategory(categoryId: Long): List<TransactionEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTransaction(transactionEntity: TransactionEntity): Long

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun updateTransaction(transactionEntity: TransactionEntity): Int

    @Transaction
    suspend fun updateTransactions(transactions: List<TransactionEntity>): List<Int> {
        return transactions.map { transaction ->
            updateTransaction(transaction)
        }
    }

    @Transaction
    suspend fun upsertTransaction(transactionEntity: TransactionEntity): Long {
        val id: Long = insertTransaction(transactionEntity)
        if (id == -1L) {
            updateTransaction(transactionEntity)
        }
        return id
    }

    @Delete
    suspend fun deleteTransaction(transactionEntity: TransactionEntity): Int

    @Query("DELETE FROM TransactionEntity")
    suspend fun clearTable()
}