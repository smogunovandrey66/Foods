package com.smogunov.domain.global.usecase

import com.smogunov.domain.global.models.presentation.Category
import com.smogunov.domain.global.repositories.CategoryRepositories
import kotlinx.coroutines.flow.map

class UseCaseCategories(private val categoryRepositories: CategoryRepositories) {
    val categories =  categoryRepositories.categories
    suspend fun load(useCash: Boolean) {
        categoryRepositories.load(useCash)
    }
}