package dev.chintansoni.repository.mapper

import dev.chintansoni.database.view.transactiondetail.TransactionDetailView
import dev.chintansoni.domain.model.TransactionDetail

fun TransactionDetailView.toDomainModel() = TransactionDetail(
    id = id,
    amount = amount,
    note = note,
    categoryId = categoryId,
    date = date,
    createdDate = createdDate,
    updatedDate = updatedDate,
    deletedDate = deletedDate,
    categoryName = categoryName,
    categoryDescription = categoryDescription,
    canBeDeleted = categoryCanBeDeleted
)