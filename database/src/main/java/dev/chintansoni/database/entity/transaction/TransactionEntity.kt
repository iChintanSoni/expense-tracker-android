package dev.chintansoni.database.entity.transaction

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
//    foreignKeys = [ForeignKey(
//        entity = CategoryEntity::class,
//        parentColumns = arrayOf("id"),
//        childColumns = arrayOf("id"),
//        onDelete = ForeignKey.NO_ACTION
//    )]
)
data class TransactionEntity(
    @PrimaryKey val id: Long,
    val amount: Float,
    val note: String?,
    val category: Int,
    val date: Long,
    val type: TransactionType,
    val createdDate: Long,
    val updatedDate: Long?,
    val deletedDate: Long?
)

sealed class TransactionType(val type: Int) {
    object Credit : TransactionType(1)
    object Debit : TransactionType(2)
}