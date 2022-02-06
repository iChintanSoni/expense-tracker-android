package dev.chintansoni.repository.mapper

import dev.chintansoni.database.entity.transaction.TransactionEntity
import dev.chintansoni.domain.model.Transaction
import dev.chintansoni.domain.model.TransactionType

fun Transaction.toDBModel(): TransactionEntity =
    TransactionEntity(
        id = id,
        amount = amount,
        note = note,
        category = category,
        date = date,
        type = type.toDBModel(),
        createdDate = createdDate,
        updatedDate = updatedDate,
        deletedDate = deletedDate
    )

fun TransactionType.toDBModel(): dev.chintansoni.database.entity.transaction.TransactionType {
    return when (type) {
        1 -> dev.chintansoni.database.entity.transaction.TransactionType.Credit
        else -> dev.chintansoni.database.entity.transaction.TransactionType.Debit
    }
}

fun dev.chintansoni.database.entity.transaction.TransactionType.toDomainModel(): TransactionType {
    return when (type) {
        1 -> TransactionType.Credit
        else -> TransactionType.Debit
    }
}

fun TransactionEntity.toDomainModel(): Transaction =
    Transaction(
        id = id,
        amount = amount,
        note = note,
        category = category,
        date = date,
        type = type.toDomainModel(),
        createdDate = createdDate,
        updatedDate = updatedDate,
        deletedDate = deletedDate
    )