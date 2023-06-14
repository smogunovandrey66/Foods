package com.smogunov.foods.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smogunov.domain.global.usecase.UseCaseCategories
import kotlinx.coroutines.launch

/**
 * Модель для категорий
 */
class CategoriesViewModel(val useCaseCategories: UseCaseCategories) : ViewModel() {
    val category = useCaseCategories.categories

    fun load(useCash: Boolean) {
        viewModelScope.launch {
            useCaseCategories.load(useCash)
        }
    }
}