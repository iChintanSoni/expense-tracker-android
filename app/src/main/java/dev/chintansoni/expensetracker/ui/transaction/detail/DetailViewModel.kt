package dev.chintansoni.expensetracker.ui.transaction.detail

import dev.chintansoni.domain.mapper.toTransaction
import dev.chintansoni.domain.repository.CategoryRepository
import dev.chintansoni.domain.repository.TransactionDetailRepository
import dev.chintansoni.domain.repository.TransactionRepository
import dev.chintansoni.expensetracker.base.BaseViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlin.coroutines.CoroutineContext

class DetailViewModel(
    private val transactionId: Long,
    private val transactionDetailRepository: TransactionDetailRepository,
    private val transactionRepository: TransactionRepository,
    private val categoryRepository: CategoryRepository
) : BaseViewModel<DetailViewContract.Event, DetailViewContract.State, DetailViewContract.Effect>() {

    init {
        if (transactionId > 0) {
            setEvent(DetailViewContract.Event.FetchDetails(transactionId))
        } else {
            setEvent(DetailViewContract.Event.ToggleEditMode)
        }
        fetchCategories()
    }

    private fun fetchCategories() {
        launchInIO {
            categoryRepository.getAllCategoriesFlow().collectLatest {
                setState { copy(categories = it) }
            }
        }
    }

    private fun fetchTransaction() {
        launchInIO {
            if (transactionId != 0L) {
                transactionDetailRepository.getTransactionByIdFlow(transactionId).collectLatest {
                    it?.let { transaction ->
                        setState {
                            copy(
                                transactionDetail = transaction,
                                amountAsString = transaction.printableAmount()
                            )
                        }
                    } ?: throw NotFoundException(transactionId)
                }
            }
        }
    }

    private fun toggleEditMode() {
        setState { copy(isEditMode = !isEditMode) }
    }

    private fun toggleDeleteMode() {
        setState { copy(isDeleteMode = !isDeleteMode) }
    }

    override fun createInitialState(): DetailViewContract.State = DetailViewContract.State.default()

    override fun handleEvent(event: DetailViewContract.Event) {
        when (event) {
            is DetailViewContract.Event.OnAmountChange -> handleAmountChange(event.amount)
            is DetailViewContract.Event.OnCategoryChange -> handleCategoryChange(event.category)
            is DetailViewContract.Event.OnDateChange -> handleDateChange(event.date)
            is DetailViewContract.Event.OnNoteChange -> handleNoteChange(event.note)
            DetailViewContract.Event.ToggleDeleteMode -> toggleDeleteMode()
            DetailViewContract.Event.ToggleEditMode -> toggleEditMode()
            DetailViewContract.Event.OnBackClick -> handleBackClick()
            DetailViewContract.Event.OnConfirmDelete -> handleConfirmDelete()
            DetailViewContract.Event.OnDoneClick -> handleDoneClick()
            is DetailViewContract.Event.FetchDetails -> fetchTransaction()
        }
    }

    private fun handleBackClick() {
        setEffect { DetailViewContract.Effect.NavigateBack }
    }

    private fun handleDoneClick() {
        if (currentState.transactionDetail.isValid()) {
            launchInIO {
                transactionRepository.upsertTransaction(currentState.transactionDetail.toTransaction())
                setEffect { DetailViewContract.Effect.NavigateBack }
            }
        }
    }

    private fun handleConfirmDelete() {
        launchInIO {
            transactionRepository.deleteTransaction(currentState.transactionDetail.toTransaction())
            setEvent(DetailViewContract.Event.OnBackClick)
        }
    }

    private fun handleNoteChange(note: String) {
        setState { copy(transactionDetail = transactionDetail.copy(note = note)) }
    }

    private fun handleDateChange(date: Long) {
        setState { copy(transactionDetail = transactionDetail.copy(date = date)) }
    }

    private fun handleCategoryChange(category: Long) {
        setState {
            copy(transactionDetail = transactionDetail.copy(categoryId = category))
        }
    }

    private fun handleAmountChange(amount: String) {
        setState {
            copy(amountAsString = amount)
        }
        val amountInFloat = amount.toFloatOrNull()
        if (amountInFloat != null) {
            setState {
                copy(
                    amountError = "",
                    transactionDetail = transactionDetail.copy(amount = amountInFloat)
                )
            }
        } else {
            setState {
                copy(
                    amountError = "Please enter a valid amount",
                    transactionDetail = transactionDetail.copy(amount = 0f)
                )
            }
        }
    }

    override fun handleException(coroutineContext: CoroutineContext, throwable: Throwable) {

    }
}
