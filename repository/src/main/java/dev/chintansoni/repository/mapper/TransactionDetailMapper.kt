package dev.chintansoni.repository.mapper

import dev.chintansoni.database.view.transactiondetail.TransactionDetailView
import dev.chintansoni.domain.model.TransactionDetail

fun TransactionDetailView.toDomainModel() = TransactionDetail(
    id = id,
    amount = amount,
    note = note,
    category = category,
    date = date,
    createdDate = createdDate,
    updatedDate = updatedDate,
    deletedDate = deletedDate,
    name = name,
    description = description,
    canBeDeleted = canBeDeleted
)

fun TransactionDetail.toDBModel() = TransactionDetailView(
    id = id,
    amount = amount,
    note = note,
    category = category,
    date = date,
    createdDate = createdDate,
    updatedDate = updatedDate,
    deletedDate = deletedDate,
    name = name,
    description = description,
    canBeDeleted = canBeDeleted
)