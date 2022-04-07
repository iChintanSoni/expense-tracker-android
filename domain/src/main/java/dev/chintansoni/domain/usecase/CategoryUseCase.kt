package dev.chintansoni.domain.usecase

import dev.chintansoni.domain.model.Category
import dev.chintansoni.domain.model.Transaction
import dev.chintansoni.domain.repository.CategoryRepository
import dev.chintansoni.domain.repository.TransactionRepository

class CategoryUseCase(
    private val transactionRepository: TransactionRepository,
    private val categoryRepository: CategoryRepository
) {

    @Throws(CategoryWithNameNotFoundException::class)
    suspend fun deleteCategory(category: Category) {
        val uncategorized: Category? = categoryRepository.getCategoryByName(Category.uncategorized)
        uncategorized?.let { it ->
            val transactions: List<Transaction> =
                transactionRepository.getAllTransactionsByCategory(categoryId = category.id)
            val updatedTransactions = transactions.map { transaction ->
                transaction.copy(category = it.id)
            }
            transactionRepository.updateTransactions(updatedTransactions)
            categoryRepository.deleteCategory(category)
        } ?: throw CategoryWithNameNotFoundException(category.name)
    }
}

data class CategoryWithNameNotFoundException(val categoryName: String) :
    Throwable("No category found with name: $categoryName")