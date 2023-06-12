package com.smogunov.foods.data.datasource

import android.util.Log
import com.smogunov.domain.global.models.network.CategoryNetwork
import com.smogunov.domain.global.models.network.DishesNetwork
import com.smogunov.foods.data.network.FoodApiService

class NetworkDataSourceImpl(private val foodApiService: FoodApiService): NetworkDataSource {
    override suspend fun loadCategory(): CategoryNetwork {
        return CategoryNetwork(
            foodApiService.getCategories().categories.filter {
                it.image_url != null
            }
        )
    }

    override suspend fun loadDishes(): DishesNetwork = foodApiService.getDishes()
}