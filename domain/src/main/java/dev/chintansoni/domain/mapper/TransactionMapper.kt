package dev.chintansoni.domain.mapper

import dev.chintansoni.domain.model.Transaction
import dev.chintansoni.domain.model.TransactionDetail

fun TransactionDetail.toTransaction() =
    Transaction(
        id = id,
        amount = amount,
        note = note,
        category = categoryId,
        date = date,
        createdDate = createdDate,
        updatedDate = updatedDate,
        deletedDate = deletedDate
    )