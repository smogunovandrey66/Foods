package com.smogunov.foods.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.smogunov.domain.global.models.database.DishDB
import com.smogunov.domain.global.models.database.DishTagCrossRefDB
import com.smogunov.domain.global.models.database.TagDB
import com.smogunov.domain.global.models.database.TagWithDishesDB

@Dao
interface DishDao {
    @Transaction
    @Query("SELECT * FROM tags WHERE name = :tagName")
    suspend fun getTagsByName(tagName: String): List<TagWithDishesDB>

    @Insert
    suspend fun insertDishes(dishes: List<DishDB>)


    @Query("SELECT * FROM tags")
    suspend fun getAllTags(): List<TagDB>

    @Insert
    suspend fun insertTags(tags: List<TagDB>)

    @Query("DELETE FROM dishes")
    suspend fun clearDishes()

    @Query("DELETE FROM tags")
    suspend fun clearTags()

    @Query("DELETE FROM dishes_tags")
    suspend fun clearDishTagRef()

    @Insert
    suspend fun insertDishTagRef(disTagRef: List<DishTagCrossRefDB>)
}