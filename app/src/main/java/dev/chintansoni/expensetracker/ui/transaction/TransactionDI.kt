package dev.chintansoni.expensetracker.ui.transaction

import dev.chintansoni.expensetracker.ui.transaction.detail.TransactionDetailViewModel
import dev.chintansoni.expensetracker.ui.transaction.list.TransactionsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val transactionModule = module {

    viewModel {
        TransactionsViewModel(
            transactionRepository = get()
        )
    }

    viewModel { (transactionId: Long) ->
        TransactionDetailViewModel(
            transactionId = transactionId,
            transactionRepository = get(),
            categoryRepository = get()
        )
    }
}