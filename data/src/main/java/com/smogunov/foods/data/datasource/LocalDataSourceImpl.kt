package com.smogunov.foods.data.datasource

import android.util.Log
import com.smogunov.domain.global.models.database.CategoryDB
import com.smogunov.domain.global.models.database.DishDB
import com.smogunov.domain.global.models.database.DishTagCrossRefDB
import com.smogunov.domain.global.models.database.TagDB
import com.smogunov.domain.global.models.database.TagWithDishesDB
import com.smogunov.foods.data.database.FoodDataBase
import kotlin.math.log

class LocalDataSourceImpl(private val dataBase: FoodDataBase) : LocalDataSource {
    override suspend fun getCategories(): List<CategoryDB> {
        return dataBase.categoryDao().getAllCategories()
    }

    override suspend fun insertCategories(data: List<CategoryDB>) {
        dataBase.categoryDao().deleteAll()
        dataBase.categoryDao().insertCategories(data)
    }

    override suspend fun getTagswithDishes(tag: String): List<TagWithDishesDB> =
        dataBase.dishDao().getTagsByName(tag)

    override suspend fun insertDishesTagAndCrossRef(dishes: List<DishDB>, tags: List<TagDB>, crossRefDB: List<DishTagCrossRefDB>) {
        Log.d("GLOBAL_TAG_LOG", "LocalDataSourceImpl insertDishesAndTag dishes=$dishes,tags=$tags")
        dataBase.dishDao().clearTags()
        dataBase.dishDao().insertTags(tags)

        dataBase.dishDao().clearDishes()
        dataBase.dishDao().insertDishes(dishes)

        dataBase.dishDao().clearDishTagRef()
        dataBase.dishDao().insertDishTagRef(crossRefDB)
    }

    override suspend fun getTags(): List<TagDB>  = dataBase.dishDao().getAllTags()
}