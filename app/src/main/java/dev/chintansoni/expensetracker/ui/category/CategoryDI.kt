package dev.chintansoni.expensetracker.ui.category

import dev.chintansoni.expensetracker.ui.category.detail.CategoryDetailViewModel
import dev.chintansoni.expensetracker.ui.category.list.CategoriesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val categoryModule = module {
    viewModel {
        CategoriesViewModel(
            categoryRepository = get()
        )
    }
    viewModel { (categoryId: Int) ->
        CategoryDetailViewModel(
            categoryId = categoryId,
            categoryRepository = get(),
            categoryUseCase = get()
        )
    }
}