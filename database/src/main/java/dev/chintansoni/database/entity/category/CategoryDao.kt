package dev.chintansoni.database.entity.category

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Query("SELECT * FROM CategoryEntity")
    fun getAllFlow(): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM CategoryEntity WHERE id = :id LIMIT 1")
    fun getByIdFlow(id: Int): Flow<CategoryEntity?>

    @Insert
    suspend fun insertCategory(categoryEntity: CategoryEntity): Long

    @Update
    suspend fun updateCategory(categoryEntity: CategoryEntity): Int

    @Delete
    suspend fun deleteCategory(categoryEntity: CategoryEntity): Int

    @Query("DELETE FROM CategoryEntity")
    suspend fun clearTable()
}