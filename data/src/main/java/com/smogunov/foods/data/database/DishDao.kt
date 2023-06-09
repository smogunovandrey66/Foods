package com.smogunov.foods.data.database

import androidx.room.Dao
import androidx.room.Query
import com.smogunov.domain.global.models.Dish

@Dao
interface DishDao {
    @Query("SELECT * FROM dish")
    suspend fun getAllDishes(): List<Dish>
}