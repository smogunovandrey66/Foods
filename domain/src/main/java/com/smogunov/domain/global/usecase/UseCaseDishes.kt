package com.smogunov.domain.global.usecase

import com.smogunov.domain.global.repositories.DishRepository

class UseCaseDishes(private val dishRepository: DishRepository) {
    val tagsWithDishes = dishRepository.dishes
    val tags = dishRepository.tags

    suspend fun load(useCash: Boolean, tag: String){
        dishRepository.load(useCash, tag)
    }
}