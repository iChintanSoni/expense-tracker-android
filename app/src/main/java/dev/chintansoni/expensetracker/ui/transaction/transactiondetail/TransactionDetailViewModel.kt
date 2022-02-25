package dev.chintansoni.expensetracker.ui.transaction.transactiondetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.chintansoni.common.DateTime
import dev.chintansoni.domain.model.Category
import dev.chintansoni.domain.model.Transaction
import dev.chintansoni.domain.repository.CategoryRepository
import dev.chintansoni.domain.repository.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TransactionDetailViewModel(
    private val transactionId: Long,
    private val transactionRepository: TransactionRepository,
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private val _transactionStateFlow = MutableStateFlow(Transaction.newInstance())
    val transactionStateFlow = _transactionStateFlow.asStateFlow()

    private val _amountErrorStateFlow = MutableStateFlow<String?>(null)
    val amountErrorStateFlow = _amountErrorStateFlow.asStateFlow()

    private val _amountStateFlow = MutableStateFlow("")
    val amountStateFlow = _amountStateFlow.asStateFlow()

    private val _categoriesStateFlow = MutableStateFlow<List<Category>>(emptyList())
    val categoriesStateFlow = _categoriesStateFlow.asStateFlow()

    private val _isEditModeStateFlow = MutableStateFlow(transactionId == 0L)
    val isEditModeStateFlow = _isEditModeStateFlow.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            if (transactionId != 0L) {
                transactionRepository
                    .getTransactionByIdFlow(transactionId)
                    .collectLatest {
                        it?.let { transaction ->
                            this@TransactionDetailViewModel._transactionStateFlow.update {
                                transaction
                            }
                            this@TransactionDetailViewModel._amountStateFlow.update {
                                transaction.printableAmount()
                            }
                        } ?: throw NotFoundException(transactionId)
                    }
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            categoryRepository
                .getAllCategoriesFlow()
                .collectLatest { categories ->
                    _categoriesStateFlow.update {
                        categories
                    }
                }
        }
    }

    fun setAmount(amount: String) {
        _amountStateFlow.update { amount }
        val amountInFloat = amount.toFloatOrNull()
        if (amountInFloat != null) {
            _amountErrorStateFlow.update { "" }
            _transactionStateFlow.update { it.copy(amount = amountInFloat) }
        } else {
            _amountErrorStateFlow.update { "Please enter a valid amount" }
            _transactionStateFlow.update { it.copy(amount = 0.0f) }
        }
    }

    fun setAmountError(amountError: String) {
        _amountErrorStateFlow.update { amountError }
    }

    fun setNote(note: String) {
        _transactionStateFlow.update { it.copy(note = note) }
    }

    fun setCategory(categoryId: Int) {
        _transactionStateFlow.update { it.copy(category = categoryId) }
    }

    fun toggleEditMode() {
        _isEditModeStateFlow.update { !it }
    }

    fun addUpdateExpense(transaction: Transaction) {
        if (transaction.isValid()) {
            viewModelScope.launch(Dispatchers.IO) {
                if (transaction.id == 0L) {
                    transactionRepository.addTransaction(transaction)
                } else {
                    transactionRepository.updateTransaction(transaction)
                }
            }
        }
    }

    suspend fun deleteExpense(transaction: Transaction) {
        transactionRepository.deleteTransaction(transaction)
    }

    fun setDate(date: DateTime) {
        _transactionStateFlow.update { it.copy(date = date.toEpochMilliseconds()) }
    }
}