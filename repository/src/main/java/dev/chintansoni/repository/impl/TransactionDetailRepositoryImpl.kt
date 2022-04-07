package dev.chintansoni.repository.impl

import dev.chintansoni.database.view.transactiondetail.TransactionDetailDao
import dev.chintansoni.domain.model.TransactionDetail
import dev.chintansoni.domain.repository.TransactionDetailRepository
import dev.chintansoni.repository.mapper.toDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class TransactionDetailRepositoryImpl(
    private val transactionDetailDao: TransactionDetailDao
) : TransactionDetailRepository {

    override fun getAllTransactionsFlow(): Flow<List<TransactionDetail>> =
        transactionDetailDao.getAllFlow().distinctUntilChanged().map { list ->
            list.map { transactionDetailView ->
                transactionDetailView.toDomainModel()
            }
        }

    override fun getTransactionByIdFlow(id: Long): Flow<TransactionDetail?> =
        transactionDetailDao.getByIdFlow(id).distinctUntilChanged().map { transactionDetailView ->
            transactionDetailView?.toDomainModel()
        }

    override fun getBetweenDates(startDate: Long, endDate: Long): Flow<List<TransactionDetail>> {
        return transactionDetailDao.getBetweenDates(
            startDateTime = startDate, endDateTime = endDate
        ).distinctUntilChanged().map { transactionDetailViewList ->
            transactionDetailViewList.map { transactionDetailView -> transactionDetailView.toDomainModel() }
        }
    }
}