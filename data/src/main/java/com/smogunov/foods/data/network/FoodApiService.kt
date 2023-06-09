package com.smogunov.foods.data.network

import com.smogunov.domain.global.models.Category
import retrofit2.http.GET

interface FoodApiService {
    @GET("058729bd-1402-4578-88de-265481fd7d54")
    suspend fun getCategories(): List<Category>
}