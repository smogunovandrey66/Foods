package com.smogunov.foods.data.datasource

import com.smogunov.domain.global.models.Category

interface NetworkDataSource {
    suspend fun load(): List<Category>
}