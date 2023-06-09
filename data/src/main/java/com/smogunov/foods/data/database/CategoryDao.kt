package com.smogunov.foods.data.database

import androidx.room.Dao
import androidx.room.Query
import com.smogunov.domain.global.models.Category

@Dao
interface CategoryDao {
    @Query("SELECT * FROM category")
    suspend fun getAllCategories(): List<Category>
}