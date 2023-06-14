package com.smogunov.domain.global.usecase

import com.smogunov.domain.global.repositories.CategoryRepositories

class UseCaseCategories(private val categoryRepositories: CategoryRepositories) {
    val categories = categoryRepositories.categories
    suspend fun load(useCash: Boolean) {
        categoryRepositories.load(useCash)
    }
}