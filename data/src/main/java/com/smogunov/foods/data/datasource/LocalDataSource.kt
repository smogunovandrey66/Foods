package com.smogunov.foods.data.datasource

import com.smogunov.domain.global.models.database.CategoryDB
import com.smogunov.domain.global.models.database.DishDB
import com.smogunov.domain.global.models.database.DishTagCrossRefDB
import com.smogunov.domain.global.models.database.DishWithCartItemDB
import com.smogunov.domain.global.models.database.TagDB
import com.smogunov.domain.global.models.database.TagWithDishesDB

interface LocalDataSource {
    suspend fun getCategories(): List<CategoryDB>
    suspend fun insertCategories(data: List<CategoryDB>)

    suspend fun getTagswithDishes(tag: String): List<TagWithDishesDB>

    suspend fun insertDishesTagAndCrossRef(dishes: List<DishDB>, tags: List<TagDB>, crossRefDB: List<DishTagCrossRefDB>)

    suspend fun getTags(): List<TagDB>

    suspend fun getAllCartItems(): List<DishWithCartItemDB>

    suspend fun changeCartItem(idDish: Int, count: Int)
}