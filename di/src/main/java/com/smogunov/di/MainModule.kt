package com.smogunov.di

import com.smogunov.domain.global.models.Category
import com.smogunov.foods.data.repository.CategoryRepositoriesImpl
import org.koin.dsl.module

val mainModule = module {
    single {
        Category(1, "hello", "url")
    }
}