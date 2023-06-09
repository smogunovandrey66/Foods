package com.smogunov.foods.data.repository

import com.smogunov.domain.global.models.Category
import com.smogunov.domain.global.repositories.CategoryRepositories
import com.smogunov.foods.data.datasource.LocalDataSource
import com.smogunov.foods.data.datasource.NetworkDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CategoryRepositoriesImpl(private val networkDataSource: NetworkDataSource, private val localDataSource: LocalDataSource): CategoryRepositories {

    private val _innerCategories = MutableStateFlow(listOf<Category>())
    private fun getInnerCategories(): StateFlow<List<Category>> {
        return _innerCategories
    }

    override suspend fun load() {
        _innerCategories.value = networkDataSource.load()
    }

    override val categories: Flow<List<Category>>
        get() = _innerCategories
}