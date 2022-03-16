package dev.chintansoni.domain.mapper

import dev.chintansoni.domain.model.Transaction
import dev.chintansoni.domain.model.TransactionDetail

fun TransactionDetail.toTransaction() =
    Transaction(id, amount, note, category, date, createdDate, updatedDate, deletedDate)