package dev.chintansoni.repository.mapper

import dev.chintansoni.database.entity.transaction.TransactionEntity
import dev.chintansoni.domain.model.Transaction

fun Transaction.toDBModel(): TransactionEntity =
    TransactionEntity(
        id = id,
        amount = amount,
        note = note,
        category = category,
        date = date,
        createdDate = createdDate,
        updatedDate = updatedDate,
        deletedDate = deletedDate
    )

fun TransactionEntity.toDomainModel(): Transaction =
    Transaction(
        id = id,
        amount = amount,
        note = note,
        category = category,
        date = date,
        createdDate = createdDate,
        updatedDate = updatedDate,
        deletedDate = deletedDate
    )