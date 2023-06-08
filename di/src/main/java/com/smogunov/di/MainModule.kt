package com.smogunov.di

import com.smogunov.domain.global.models.Category
import org.koin.dsl.module

val mainModule = module {
    single<Category> {
        Category(1, "hello", "url")
    }
}