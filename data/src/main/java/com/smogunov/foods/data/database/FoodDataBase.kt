package com.smogunov.foods.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.smogunov.domain.global.models.Category
import com.smogunov.domain.global.models.Dish

@Database([Category::class, Dish::class], version = 1)
abstract class FoodDataBase: RoomDatabase() {
    abstract fun categoryDao(): CategoryDao

    abstract fun dishDao(): DishDao
}