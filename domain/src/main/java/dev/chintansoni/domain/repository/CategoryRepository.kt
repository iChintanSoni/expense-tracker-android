package dev.chintansoni.domain.repository

import dev.chintansoni.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {

    fun getAllCategoriesFlow(): Flow<List<Category>>

    fun getCategoryByIdFlow(id: Int): Flow<Category?>

    suspend fun getCategoryById(id: Long): Category?

    suspend fun getCategoryByName(name: String): Category?

    suspend fun addCategory(category: Category): Long

    suspend fun addCategories(categories: List<Category>): List<Long>

    suspend fun updateCategory(category: Category): Int

    suspend fun deleteCategory(category: Category): Int

    suspend fun upsertCategory(category: Category): Long

    suspend fun clear()
}