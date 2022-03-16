package dev.chintansoni.expensetracker.ui.category.list

import dev.chintansoni.domain.repository.CategoryRepository
import dev.chintansoni.expensetracker.base.BaseViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlin.coroutines.CoroutineContext

class ListViewModel(
    private val categoryRepository: CategoryRepository
) : BaseViewModel<ListViewContract.Event, ListViewContract.State, ListViewContract.Effect>() {

    init {
        setEvent(ListViewContract.Event.FetchCategories)
    }

    override fun createInitialState(): ListViewContract.State = ListViewContract.State.default()

    override fun handleEvent(event: ListViewContract.Event) {
        when (event) {
            ListViewContract.Event.FetchCategories -> {
                fetchAllCategories()
            }
            is ListViewContract.Event.NavigateToDetail -> {
                setEffect { ListViewContract.Effect.NavigateToDetail(event.id) }
            }
            is ListViewContract.Event.NavigateBack -> {
                setEffect { ListViewContract.Effect.NavigateBack }
            }
        }
    }

    private fun fetchAllCategories() {
        launchInIO {
            categoryRepository.getAllCategoriesFlow().collectLatest {
                setState { copy(categoryList = it) }
            }
        }
    }

    override fun handleException(coroutineContext: CoroutineContext, throwable: Throwable) {

    }
}