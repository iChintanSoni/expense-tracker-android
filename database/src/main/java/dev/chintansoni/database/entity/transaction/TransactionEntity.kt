package dev.chintansoni.database.entity.transaction

import androidx.room.Entity
import androidx.room.PrimaryKey

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

sealed class TransactionType(val type: Int) {
    object Credit : TransactionType(1)
    object Debit : TransactionType(2)
}