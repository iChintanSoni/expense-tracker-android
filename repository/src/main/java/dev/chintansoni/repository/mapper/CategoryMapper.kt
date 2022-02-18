package dev.chintansoni.repository.mapper

import dev.chintansoni.database.entity.category.CategoryEntity
import dev.chintansoni.domain.model.Category

fun Category.toDBModel(): CategoryEntity =
    CategoryEntity(
        id = id,
        name = name,
        description = description,
        canBeDeleted = canBeDeleted
    )

fun CategoryEntity.toDomainModel(): Category =
    Category(
        id = id,
        name = name,
        description = description,
        canBeDeleted = canBeDeleted
    )