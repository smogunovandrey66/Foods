package com.smogunov.foods.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smogunov.di.usecaseModule
import com.smogunov.domain.global.models.Category
import com.smogunov.domain.global.usecase.UseCaseCategories
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CategoriesViewModel(val useCaseCategories: UseCaseCategories): ViewModel() {
    private val _categories = MutableStateFlow(emptyList<Category>())


    val category: StateFlow<List<Category>> = _categories



    fun load() {
        viewModelScope.launch {
            useCaseCategories.categories.collectLatest {
                _categories.value = it
            }
        }
    }
}