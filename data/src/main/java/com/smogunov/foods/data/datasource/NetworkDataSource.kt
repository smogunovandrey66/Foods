package com.smogunov.foods.data.datasource

import com.smogunov.domain.global.models.network.CategoryNetwork
import com.smogunov.domain.global.models.network.DishesNetwork

interface NetworkDataSource {
    suspend fun loadCategory(): CategoryNetwork

    suspend fun loadDishes(): DishesNetwork
}