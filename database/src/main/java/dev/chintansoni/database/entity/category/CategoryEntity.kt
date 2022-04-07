package dev.chintansoni.database.entity.category

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [
        Index(value = ["name"], unique = true)
    ]
)
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val description: String,
    val canBeDeleted: Boolean
)
