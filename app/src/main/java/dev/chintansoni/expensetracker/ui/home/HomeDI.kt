package dev.chintansoni.expensetracker.ui.home

import dev.chintansoni.expensetracker.ui.home.list.ListViewModel
import dev.chintansoni.expensetracker.ui.home.profile.ProfileViewModel
import dev.chintansoni.expensetracker.ui.transaction.transactiondetail.TransactionDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val homeModule = module {
    viewModel {
        ProfileViewModel(get())
    }
    viewModel {
        ListViewModel(get())
    }
    viewModel { (transactionId: Long) ->
        TransactionDetailViewModel(
            transactionId = transactionId,
            transactionRepository = get(),
            categoryRepository = get()
        )
    }
}