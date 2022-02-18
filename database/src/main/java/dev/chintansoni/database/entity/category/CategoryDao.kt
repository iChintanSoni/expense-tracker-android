package dev.chintansoni.database.entity.category

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Query("SELECT * FROM CategoryEntity")
    fun getAllFlow(): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM CategoryEntity WHERE id = :id LIMIT 1")
    fun getByIdFlow(id: Int): Flow<CategoryEntity?>

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
    suspend fun upsertCategory(categoryEntity: CategoryEntity) {
        println("Dao: $categoryEntity")
        val id: Long = insertCategory(categoryEntity)
        println("Is Inserted: $id")
        if (id == -1L) {
            val count = updateCategory(categoryEntity)
            println("Is Updated: $count")
        }
    }

    @Query("DELETE FROM CategoryEntity")
    suspend fun clearTable()
}