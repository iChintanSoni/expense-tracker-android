package dev.chintansoni.database.view.transactiondetail

import androidx.room.DatabaseView

@DatabaseView(
    "SELECT " +
        "transactionEntity.id," +
        "transactionEntity.amount," +
        "transactionEntity.note," +
        "transactionEntity.category," +
        "transactionEntity.date," +
        "transactionEntity.createdDate," +
        "categoryEntity.name," +
        "categoryEntity.description," +
        "categoryEntity.canBeDeleted" +
        " FROM transactionEntity " +
        "INNER JOIN categoryEntity ON transactionEntity.category = categoryEntity.id"
)
data class TransactionDetailView(
    val id: Long,
    val amount: Float,
    val note: String?,
    val category: Int,
    val date: Long,
    val createdDate: Long,
    val updatedDate: Long?,
    val deletedDate: Long?,
    val name: String,
    val description: String,
    val canBeDeleted: Boolean
)