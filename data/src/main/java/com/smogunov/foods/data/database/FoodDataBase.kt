package com.smogunov.foods.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database([com.smogunov.domain.global.models.Category::class], version = 1)
abstract class FoodDataBase: RoomDatabase() {
}