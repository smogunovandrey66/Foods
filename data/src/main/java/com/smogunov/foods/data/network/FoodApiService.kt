package com.smogunov.foods.data.network

import com.smogunov.domain.global.models.network.CategoryNetwork
import com.smogunov.domain.global.models.network.DishesNetwork
import retrofit2.http.GET

interface FoodApiService {
    @GET("058729bd-1402-4578-88de-265481fd7d54")
    suspend fun getCategories(): CategoryNetwork

    @GET("c7a508f2-a904-498a-8539-09d96785446e")
    suspend fun getDishes(): DishesNetwork
}