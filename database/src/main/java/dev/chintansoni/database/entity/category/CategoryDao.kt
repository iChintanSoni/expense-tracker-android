package dev.chintansoni.database.entity.category

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Query("SELECT * FROM CategoryEntity")
    fun getAllFlow(): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM CategoryEntity WHERE id = :id LIMIT 1")
    fun getByIdFlow(id: Int): Flow<CategoryEntity?>

    @Query("SELECT * FROM CategoryEntity WHERE id = :id LIMIT 1")
    fun getById(id: Long): CategoryEntity?

    @Query("SELECT * FROM CategoryEntity WHERE name = :name LIMIT 1")
    suspend fun getByName(name: String): CategoryEntity?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCategory(categoryEntity: CategoryEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categoryEntity: List<CategoryEntity>): List<Long>

    @Update(onConflict = OnConflictStrategy.IGNORE)
    suspend fun updateCategory(categoryEntity: CategoryEntity): Int

    @Delete
    suspend fun deleteCategory(categoryEntity: CategoryEntity): Int

    @Transaction
    suspend fun upsertCategory(categoryEntity: CategoryEntity): Long {
        val id: Long = insertCategory(categoryEntity)
        if (id == -1L) {
            updateCategory(categoryEntity)
        }
        return id
    }

    @Query("DELETE FROM CategoryEntity")
    suspend fun clearTable()
}