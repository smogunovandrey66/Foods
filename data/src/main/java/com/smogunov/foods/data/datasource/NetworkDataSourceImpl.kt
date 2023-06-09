package com.smogunov.foods.data.datasource

import com.smogunov.foods.data.network.FoodApiService

class NetworkDataSourceImpl(private val foodApiService: FoodApiService): NetworkDataSource {
    override suspend fun load() = foodApiService.getCategories()
}