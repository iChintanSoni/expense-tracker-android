package dev.chintansoni.domain.model

data class Transaction(
    val id: Int = (0..100).random(),
    val amount: Float = (0..100).random().toFloat(),
    val note: String = "Sample Note",
    val category: String = "General",
    val date: Long = System.currentTimeMillis(),
    val type: TransactionType = TransactionType.Debit,
    val createdDate: Long = System.currentTimeMillis(),
    val updatedDate: Long = System.currentTimeMillis(),
    val deletedDate: Long = System.currentTimeMillis()
)

sealed class TransactionType(val type: Int) {
    object Credit : TransactionType(1)
    object Debit : TransactionType(2)
}