package dev.chintansoni.repository

import dev.chintansoni.database.entity.category.CategoryDao
import dev.chintansoni.domain.model.Category
import dev.chintansoni.domain.repository.CategoryRepository
import dev.chintansoni.repository.mapper.toDBModel
import dev.chintansoni.repository.mapper.toDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class CategoryRepositoryImpl(private val categoryDao: CategoryDao) :
    CategoryRepository {

    override fun getAllCategoriesFlow(): Flow<List<Category>> {
        return categoryDao.getAllFlow().distinctUntilChanged().map { list ->
            list.map { categoryEntity ->
                categoryEntity.toDomainModel()
            }
        }
    }

    override fun getCategoryByIdFlow(id: Int): Flow<Category?> {
        return categoryDao.getByIdFlow(id).distinctUntilChanged().map { categoryEntity ->
            categoryEntity?.toDomainModel()
        }
    }

    override suspend fun getCategoryByName(name: String): Category? {
        return categoryDao.getByName(name)?.toDomainModel()
    }

    override suspend fun addCategory(category: Category): Long {
        return categoryDao.insertCategory(category.toDBModel())
    }

    override suspend fun addCategories(categories: List<Category>): List<Long> {
        return categoryDao.insertCategories(categories.map { it.toDBModel() })
    }

    override suspend fun updateCategory(category: Category): Int {
        return categoryDao.updateCategory(category.toDBModel())
    }

    override suspend fun deleteCategory(category: Category): Int {
        return categoryDao.deleteCategory(category.toDBModel())
    }

    override suspend fun upsertCategory(category: Category) {
        categoryDao.upsertCategory(category.toDBModel())
    }

    override suspend fun clear() {
        return categoryDao.clearTable()
    }
}