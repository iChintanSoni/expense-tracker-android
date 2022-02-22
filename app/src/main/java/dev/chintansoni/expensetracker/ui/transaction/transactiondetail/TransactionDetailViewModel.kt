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

    private val _categoriesStateFlow = MutableStateFlow<List<Category>>(emptyList())
    val categoriesStateFlow = _categoriesStateFlow.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            if (transactionId != 0L) {
                transactionRepository
                    .getTransactionByIdFlow(transactionId)
                    .collectLatest { transaction ->
                        transaction?.let {
                            this@TransactionDetailViewModel._transactionStateFlow.value = it
                        } ?: throw NotFoundException(transactionId)
                    }
            }
        }
        viewModelScope.launch {
            categoryRepository
                .getAllCategoriesFlow()
                .collectLatest {
                    _categoriesStateFlow.value = it
                }
        }
    }

    fun setAmount(amount: String) {
        val amountInFloat = amount.toFloat()
        _transactionStateFlow.value = _transactionStateFlow.value.copy(amount = amountInFloat)
    }

    fun setAmountError(amountError: String) {
        _amountErrorStateFlow.value = amountError
    }

    fun setNote(note: String) {
        _transactionStateFlow.value = _transactionStateFlow.value.copy(note = note)
    }

    fun setCategory(categoryId: Int) {
        _transactionStateFlow.value = _transactionStateFlow.value.copy(category = categoryId)
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
        _transactionStateFlow.value =
            _transactionStateFlow.value.copy(date = date.toEpochMilliseconds())
    }
}