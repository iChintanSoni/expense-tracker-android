package dev.chintansoni.database

import androidx.room.TypeConverter
import dev.chintansoni.domain.model.TransactionType

class TypeConverter {

    @TypeConverter
    fun intToTransactionType(value: Int): TransactionType {
        return when (value) {
            1 -> TransactionType.Credit
            else -> TransactionType.Debit
        }
    }

    @TypeConverter
    fun transactionTypeToInt(transactionType: TransactionType): Int {
        return transactionType.type
    }
}