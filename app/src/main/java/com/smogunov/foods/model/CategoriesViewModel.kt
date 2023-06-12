package com.smogunov.foods.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smogunov.domain.global.models.database.CategoryDB
import com.smogunov.domain.global.models.network.CategoryNetwork
import com.smogunov.domain.global.resultdata.ResultData
import com.smogunov.domain.global.usecase.UseCaseCategories
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CategoriesViewModel(val useCaseCategories: UseCaseCategories): ViewModel() {
    val category = useCaseCategories.categories

    fun load(useCash: Boolean) {
        viewModelScope.launch {
            useCaseCategories.load(useCash)
        }
    }
}