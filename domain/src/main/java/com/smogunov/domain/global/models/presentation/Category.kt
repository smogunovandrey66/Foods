package com.smogunov.domain.global.models.presentation

import kotlin.Int

/**
 * Информация о категории для слоя Presentation
 */
data class Category(
    val id: Int,
    val name: String,
    val imageUrl: String
)