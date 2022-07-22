package dev.chintansoni.expensetracker.ui.transaction

import dev.chintansoni.expensetracker.ui.transaction.detail.DetailViewModel
import dev.chintansoni.expensetracker.ui.transaction.list.ListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val transactionModule = module {

    viewModel {
        ListViewModel(
            transactionDetailRepository = get()
        )
    }

    viewModel { (transactionId: Long) ->
        DetailViewModel(
            transactionId = transactionId,
            transactionDetailRepository = get(),
            transactionRepository = get(),
            categoryRepository = get()
        )
    }
}
