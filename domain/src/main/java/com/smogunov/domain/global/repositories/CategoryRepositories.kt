package com.smogunov.domain.global.repositories

import com.smogunov.domain.global.models.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface CategoryRepositories {
    val categories: Flow<List<Category>>
    suspend fun load()
}