package com.smogunov.foods.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.smogunov.domain.global.models.database.CategoryDB

@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories")
    suspend fun getAllCategories(): List<CategoryDB>

    @Insert
    suspend fun insertCategories(data: List<CategoryDB>)

    @Query("DELETE FROM categories")
    suspend fun deleteAll()
}