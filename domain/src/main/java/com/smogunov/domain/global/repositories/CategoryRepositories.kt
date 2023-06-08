package com.smogunov.domain.global.repositories

import com.smogunov.domain.global.models.Category

interface CategoryRepositories {
    fun getCategories(): List<Category>
}