package com.smogunov.foods.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.smogunov.domain.global.models.database.CategoryDB
import com.smogunov.domain.global.models.database.DishDB
import com.smogunov.domain.global.models.database.DishTagCrossRefDB
import com.smogunov.domain.global.models.database.TagDB

@Database([CategoryDB::class, DishDB::class, TagDB::class, DishTagCrossRefDB::class], version = 1)
abstract class FoodDataBase: RoomDatabase() {
    abstract fun categoryDao(): CategoryDao

    abstract fun dishDao(): DishDao
}