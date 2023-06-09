package com.smogunov.domain.global.usecase

import com.smogunov.domain.global.repositories.DishRepository

class UseCaseDishes(private val dishRepository: DishRepository) {
    operator fun invoke() = dishRepository.dishes
}