package com.smogunov.foods.di

import com.smogunov.foods.model.CategoriesViewModel
import org.koin.dsl.module


val viewmodelModule = module {
    single {
        CategoriesViewModel(get())
    }
}