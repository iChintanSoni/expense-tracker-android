package dev.chintansoni.database.view.transactiondetail

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDetailDao {

    @Query("SELECT * FROM TransactionDetailView ORDER BY createdDate DESC")
    fun getAllFlow(): Flow<List<TransactionDetailView>>
}