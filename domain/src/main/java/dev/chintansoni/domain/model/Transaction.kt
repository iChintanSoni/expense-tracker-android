package dev.chintansoni.domain.model

import dev.chintansoni.common.currentDateTimeInMillis

data class Transaction(
    val id: Long,
    val amount: Float,
    val note: String?,
    val category: Int,
    val date: Long,
    val createdDate: Long,
    val updatedDate: Long?,
    val deletedDate: Long?
) {
    companion object {
        fun dummyInstance(): Transaction = Transaction(
            id = (0..100).random().toLong(),
            amount = (0..100).random().toFloat(),
            note = "Dummy Note",
            category = 0,
            date = currentDateTimeInMillis(),
            createdDate = currentDateTimeInMillis(),
            updatedDate = null,
            deletedDate = null
        )

        fun newInstance(): Transaction = Transaction(
            id = 0,
            amount = 0f,
            note = null,
            category = 0,
            date = currentDateTimeInMillis(),
            createdDate = currentDateTimeInMillis(),
            updatedDate = null,
            deletedDate = null
        )
    }
}