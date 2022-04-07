package dev.chintansoni.database.view.transactiondetail

import androidx.room.DatabaseView

@DatabaseView(
    "SELECT " +
        "transactionEntity.id," +
        "transactionEntity.amount," +
        "transactionEntity.note," +
        "transactionEntity.date," +
        "transactionEntity.createdDate," +
        "transactionEntity.updatedDate," +
        "transactionEntity.deletedDate," +
        "transactionEntity.category as categoryId," +
        "categoryEntity.name as categoryName," +
        "categoryEntity.description as categoryDescription," +
        "categoryEntity.canBeDeleted as categoryCanBeDeleted" +
        " FROM transactionEntity " +
        "INNER JOIN categoryEntity ON transactionEntity.category = categoryEntity.id"
)
data class TransactionDetailView(
    val id: Long,
    val amount: Float,
    val note: String?,
    val date: Long,
    val createdDate: Long,
    val updatedDate: Long?,
    val deletedDate: Long?,
    val categoryId: Long,
    val categoryName: String,
    val categoryDescription: String,
    val categoryCanBeDeleted: Boolean
)