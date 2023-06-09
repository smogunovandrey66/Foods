package com.smogunov.foods.data.repository

import com.smogunov.domain.global.models.Dish
import com.smogunov.domain.global.repositories.DishRepository
import com.smogunov.foods.data.datasource.LocalDataSource
import com.smogunov.foods.data.datasource.NetworkDataSource
import kotlinx.coroutines.flow.StateFlow

class DishRepositoryImpl(networkDataSource: NetworkDataSource, localDataSource: LocalDataSource): DishRepository {
    override val dishes: StateFlow<List<Dish>>
        get() = TODO("Not yet implemented")
}