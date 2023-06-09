package com.smogunov.domain.global.repositories

import com.smogunov.domain.global.models.Dish
import kotlinx.coroutines.flow.StateFlow

interface DishRepository {
    val dishes: StateFlow<List<Dish>>
}