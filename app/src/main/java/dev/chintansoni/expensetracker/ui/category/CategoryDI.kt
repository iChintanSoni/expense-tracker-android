package dev.chintansoni.expensetracker.ui.category

import dev.chintansoni.expensetracker.ui.category.list.CategoriesViewModel
import dev.chintansoni.expensetracker.ui.category.manage.ManageCategoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val categoryModule = module {
    viewModel {
        CategoriesViewModel(
            categoryRepository = get()
        )
    }
    viewModel { (categoryId: Int) ->
        ManageCategoryViewModel(
            categoryId = categoryId,
            categoryRepository = get(),
            categoryUseCase = get()
        )
    }
}