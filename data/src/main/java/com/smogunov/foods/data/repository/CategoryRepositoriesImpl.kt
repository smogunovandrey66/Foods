package com.smogunov.foods.data.repository

import com.smogunov.domain.global.models.database.CategoryDB
import com.smogunov.domain.global.models.presentation.Category
import com.smogunov.domain.global.repositories.CategoryRepositories
import com.smogunov.domain.global.resultdata.ResultData
import com.smogunov.foods.data.datasource.LocalDataSource
import com.smogunov.foods.data.datasource.NetworkDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CategoryRepositoriesImpl(private val networkDataSource: NetworkDataSource, private val localDataSource: LocalDataSource): CategoryRepositories {

    private val _categories: MutableStateFlow<ResultData> = MutableStateFlow(ResultData.Loading)

    override suspend fun load(useCash: Boolean) {
        try {
            if (useCash) {
                _categories.value = ResultData.Success(localDataSource.getCategories())
            } else {
                val categoryNetwork = networkDataSource.loadCategory()

                val dataDb = categoryNetwork.categories.map {
                    CategoryDB(it.id ?: 0, it.name ?: "empty name", it.image_url ?: "empty image")
                }
                localDataSource.insertCategories(dataDb)

                val dataPresenter = categoryNetwork.categories.map {
                    Category(it.id ?: 0, it.name ?: "empty name", it.image_url ?: "empty image")
                }
                _categories.value = ResultData.Success(dataPresenter)
            }
        } catch (e: Exception) {
            _categories.value = ResultData.Error(e.message ?: "Error load categories[${e.javaClass.name}]")
        }
    }

    override val categories: StateFlow<ResultData>
        get() = _categories
}