package dev.chintansoni.domain.model

import dev.chintansoni.common.currentDateTimeInMillis

data class TransactionDetail(
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
    val canBeDeleted: Boolean
) {
    companion object {

        fun dummyList(): List<TransactionDetail> {
            val transactionDetailList = mutableListOf<TransactionDetail>()
            repeat(10) {
                transactionDetailList.add(dummyInstance())
            }
            return transactionDetailList
        }

        fun dummyInstance() = TransactionDetail(
            id = (0..100).random().toLong(),
            amount = (0..100).random().toFloat(),
            note = "Dummy Note",
            categoryId = 0,
            date = currentDateTimeInMillis(),
            createdDate = currentDateTimeInMillis(),
            updatedDate = null,
            deletedDate = null,
            categoryName = "",
            categoryDescription = "",
            canBeDeleted = false
        )

        fun defaultInstance() = TransactionDetail(
            id = 0L,
            amount = 0f,
            note = "",
            categoryId = 0,
            date = currentDateTimeInMillis(),
            createdDate = currentDateTimeInMillis(),
            updatedDate = null,
            deletedDate = null,
            categoryName = "",
            categoryDescription = "",
            canBeDeleted = false
        )
    }

    fun printableAmount(): String {
        return if (amount > 0.0) amount.toString() else ""
    }

    fun isValid(): Boolean {
        return amount > 0.0 && categoryId > 0 && date > 0
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