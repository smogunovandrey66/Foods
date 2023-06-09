package com.smogunov.di

import androidx.room.Room
import com.smogunov.foods.data.database.FoodDataBase
import org.koin.dsl.module


const val DATABASE_NAME = "food.db"

val databaseModule = module {
    single {
        Room.databaseBuilder(get(), FoodDataBase::class.java, DATABASE_NAME).build()
    }

    single {
        provideCategoryDao(get())
    }

    single {
        provideDishDao(get())
    }
}

fun provideCategoryDao(dataBase: FoodDataBase) = dataBase.categoryDao()

fun provideDishDao(dataBase: FoodDataBase) = dataBase.dishDao()