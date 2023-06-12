package com.smogunov.domain.global.models.presentation

data class Dish(
    val id: Int,
    val name: String,
    val price: Int,
    val weight: Int,
    val description: String,
    val imageUrl: String
)

data class TagWithDishes(
    val tagName: String,
    val dishes: List<Dish>
)

data class Tag(
    val tagName: String
)