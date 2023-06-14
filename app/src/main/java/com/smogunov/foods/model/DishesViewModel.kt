package com.smogunov.foods.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smogunov.domain.global.usecase.UseCaseDishes
import kotlinx.coroutines.launch

/**
 * Модель для блюд
 */
class DishesViewModel(private val useCaseDishes: UseCaseDishes) : ViewModel() {
    val tagWithDishes = useCaseDishes.tagsWithDishes
    val tags = useCaseDishes.tags
    fun load(useCash: Boolean, tag: String) {
        viewModelScope.launch {
            useCaseDishes.load(useCash, tag)
        }
    }
}