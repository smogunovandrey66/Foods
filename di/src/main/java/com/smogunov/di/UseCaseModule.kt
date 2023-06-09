package com.smogunov.di

import com.smogunov.domain.global.repositories.CategoryRepositories
import com.smogunov.domain.global.repositories.DishRepository
import com.smogunov.domain.global.usecase.UseCaseCategories
import com.smogunov.domain.global.usecase.UseCaseDishes
import com.smogunov.foods.data.datasource.LocalDataSource
import com.smogunov.foods.data.datasource.LocalDataSourceImpl
import com.smogunov.foods.data.datasource.NetworkDataSource
import com.smogunov.foods.data.datasource.NetworkDataSourceImpl
import com.smogunov.foods.data.repository.CategoryRepositoriesImpl
import com.smogunov.foods.data.repository.DishRepositoryImpl
import org.koin.dsl.module

val usecaseModule = module {
    single<LocalDataSource> {
        LocalDataSourceImpl(get())
    }

    single<NetworkDataSource> {
        NetworkDataSourceImpl(get())
    }

    single<CategoryRepositories> {
        CategoryRepositoriesImpl(get(), get())
    }

    single<DishRepository> {
        DishRepositoryImpl(get(), get())
    }

    single {
        UseCaseCategories(get())
    }

    single {
        UseCaseDishes(get())
    }
}