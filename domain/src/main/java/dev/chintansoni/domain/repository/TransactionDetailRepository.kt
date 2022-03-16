package dev.chintansoni.domain.repository

import dev.chintansoni.domain.model.TransactionDetail
import kotlinx.coroutines.flow.Flow

interface TransactionDetailRepository {

    fun getAllTransactionsFlow(): Flow<List<TransactionDetail>>

    fun getTransactionByIdFlow(id: Long): Flow<TransactionDetail?>
}