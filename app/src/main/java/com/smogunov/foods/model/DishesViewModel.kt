package com.smogunov.foods.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smogunov.domain.global.usecase.UseCaseDishes
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Модель для блюд
 */
class DishesViewModel(val useCaseDishes: UseCaseDishes) : ViewModel() {
    val tagWithDishes = useCaseDishes.tagsWithDishes
    val tags = useCaseDishes.tags

    val selectedTag: StateFlow<String> = MutableStateFlow("Все меню")

    fun load(useCash: Boolean, tag: String) {
        viewModelScope.launch {
            useCaseDishes.load(useCash, tag)
        }
    }
}