package dev.chintansoni.expensetracker.ui.category

import dev.chintansoni.expensetracker.ui.category.detail.DetailViewModel
import dev.chintansoni.expensetracker.ui.category.list.ListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val categoryModule = module {
    viewModel {
        ListViewModel(
            categoryRepository = get()
        )
    }
    viewModel { (categoryId: Long) ->
        DetailViewModel(
            categoryId = categoryId,
            categoryRepository = get(),
            categoryUseCase = get()
        )
    }
}