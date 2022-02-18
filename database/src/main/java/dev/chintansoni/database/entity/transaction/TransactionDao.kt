package dev.chintansoni.database.entity.transaction

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Query("SELECT * FROM TransactionEntity")
    fun getAllFlow(): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM TransactionEntity WHERE id = :id LIMIT 1")
    fun getByIdFlow(id: Long): Flow<TransactionEntity?>

    @Query("SELECT * FROM TransactionEntity WHERE category = :categoryId")
    suspend fun getAllByCategory(categoryId: Int): List<TransactionEntity>

    @Insert
    suspend fun insertTransaction(transactionEntity: TransactionEntity): Long

    @Transaction
    suspend fun updateTransactions(transactions: List<TransactionEntity>): List<Int> {
        return transactions.map { transaction ->
            updateTransaction(transaction)
        }
    }

    @Update(onConflict = REPLACE)
    suspend fun updateTransaction(transactionEntity: TransactionEntity): Int

    @Delete
    suspend fun deleteTransaction(transactionEntity: TransactionEntity): Int

    @Query("DELETE FROM TransactionEntity")
    suspend fun clearTable()
}