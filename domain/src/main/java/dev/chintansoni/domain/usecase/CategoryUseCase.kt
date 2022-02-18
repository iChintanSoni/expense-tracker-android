package dev.chintansoni.domain.usecase

import dev.chintansoni.domain.model.Category
import dev.chintansoni.domain.model.Transaction
import dev.chintansoni.domain.model.UncategorizedCategory
import dev.chintansoni.domain.repository.CategoryRepository
import dev.chintansoni.domain.repository.TransactionRepository

class CategoryUseCase(
    private val transactionRepository: TransactionRepository,
    private val categoryRepository: CategoryRepository
) {

    @Throws(CategoryNotFoundException::class)
    suspend fun deleteCategory(category: Category) {
        val uncategorized: Category? = categoryRepository.getCategoryByName(UncategorizedCategory)
        val transactions: List<Transaction> =
            transactionRepository.getAllTransactionsByCategory(categoryId = category.id)
        uncategorized?.let { it ->
            val updatedTransactions = transactions.map { transaction ->
                transaction.copy(category = it.id)
            }
            transactionRepository.updateTransactions(updatedTransactions)
        } ?: throw CategoryNotFoundException(category.name)
    }
}

data class CategoryNotFoundException(val categoryName: String) :
    Exception("No category found with name: $categoryName")