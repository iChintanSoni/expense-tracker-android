package dev.chintansoni.database.entity.transaction

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.chintansoni.domain.model.TransactionType

@Entity
data class TransactionEntity(
    @PrimaryKey val id: Int,
    val amount: Float,
    val note: String,
    val category: String,
    val date: Long,
    val type: TransactionType,
    val createdDate: Long,
    val updatedDate: Long,
    val deletedDate: Long
)