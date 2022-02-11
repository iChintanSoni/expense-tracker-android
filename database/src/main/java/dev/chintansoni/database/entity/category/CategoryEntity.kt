package dev.chintansoni.database.entity.category

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CategoryEntity(@PrimaryKey val id: Int, val name: String, val description: String)
