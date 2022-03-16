package dev.chintansoni.expensetracker.ui.transaction.detail

open class TransactionDetailException(message: String?) : Exception(message)
class NotFoundException(id: Long) :
    TransactionDetailException("Transaction with id: $id not found.")
