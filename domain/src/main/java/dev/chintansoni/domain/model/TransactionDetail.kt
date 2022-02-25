package dev.chintansoni.domain.model

import dev.chintansoni.common.currentDateTimeInMillis

data class TransactionDetail(
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
) {
    companion object {
        fun dummyInstance() = TransactionDetail(
            id = (0..100).random().toLong(),
            amount = (0..100).random().toFloat(),
            note = "Dummy Note",
            category = 0,
            date = currentDateTimeInMillis(),
            createdDate = currentDateTimeInMillis(),
            updatedDate = null,
            deletedDate = null,
            name = "",
            description = "",
            canBeDeleted = false
        )
    }
}

/**
 * Generates dummy list of categories
 */
fun generateDummyTransactions(): List<TransactionDetail> {
    val transactionList = mutableListOf<TransactionDetail>()
    repeat(1000) {
        transactionList.add(TransactionDetail.dummyInstance())
    }
    return transactionList
}