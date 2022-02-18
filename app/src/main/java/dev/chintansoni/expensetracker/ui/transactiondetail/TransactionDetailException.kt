package dev.chintansoni.expensetracker.ui.transactiondetail

open class TransactionDetailException(message: String?) : Exception(message)
class NotFoundException(id: Long) :
    TransactionDetailException("Transaction with id: $id not found.")

class UnknownException(throwable: Throwable) : TransactionDetailException(throwable.message)
object NoException : TransactionDetailException(null)
