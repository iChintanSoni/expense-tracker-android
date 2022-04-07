package dev.chintansoni.database.entity.transaction

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val amount: Float,
    val note: String?,
    val category: Long,
    val date: Long,
    val createdDate: Long,
    val updatedDate: Long?,
    val deletedDate: Long?
)