package com.smogunov.foods.data.repository

import android.util.Log
import com.smogunov.domain.global.models.database.DishDB
import com.smogunov.domain.global.models.database.DishTagCrossRefDB
import com.smogunov.domain.global.models.database.TagDB
import com.smogunov.domain.global.models.presentation.Dish
import com.smogunov.domain.global.models.presentation.Tag
import com.smogunov.domain.global.repositories.DishRepository
import com.smogunov.domain.global.resultdata.ResultData
import com.smogunov.foods.data.datasource.LocalDataSource
import com.smogunov.foods.data.datasource.NetworkDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DishRepositoryImpl(
    private val networkDataSource: NetworkDataSource,
    private val localDataSource: LocalDataSource
) : DishRepository {

    private val _dishes: MutableStateFlow<ResultData> = MutableStateFlow(ResultData.Loading)
    private val _tags: MutableStateFlow<ResultData> = MutableStateFlow(ResultData.Loading)
    override val dishes: StateFlow<ResultData>
        get() = _dishes

    override val tags: StateFlow<ResultData>
        get() = _tags

    override suspend fun load(useCash: Boolean, tag: String) {
        try {
            Log.d("GLOBAL_TAG_LOG", "DishRepositoryImpl load before,useCash=$useCash")
            if (!useCash) {
                val setTagsDB: MutableSet<String> = mutableSetOf()
                val dishesNetwork = networkDataSource.loadDishes().dishes.filter {
                    it.imageUrl != null && it.name != null
                }
                Log.d("GLOBAL_TAG_LOG", "DishRepositoryImpl load count=${dishesNetwork.count()}")
                dishesNetwork.forEach {
                    val boolean = it.imageUrl == null
                    Log.d(
                        "GLOBAL_TAG_LOG",
                        "DishRepositoryImpl load imageUrlNull=$boolean, dishesItem=$it"
                    )
                }
                val listDishTagCrossRefDB: MutableList<DishTagCrossRefDB> = mutableListOf()
                val dishesDB = dishesNetwork.map { dishesNetworkItem ->
                    dishesNetworkItem.tags?.forEach { tagName ->
                        setTagsDB.add(tagName)
                        listDishTagCrossRefDB.add(
                            DishTagCrossRefDB(
                                dishesNetworkItem.id ?: 0,
                                tagName
                            )
                        )
                    }
                    DishDB(
                        dishesNetworkItem.id ?: 0,
                        dishesNetworkItem.name ?: "empty name",
                        dishesNetworkItem.price ?: 0,
                        dishesNetworkItem.weight ?: 0,
                        dishesNetworkItem.description ?: "empty description",
                        dishesNetworkItem.imageUrl ?: "empty url"
                    )
                }
                Log.d("GLOBAL_TAG_LOG", "DishRepositoryImpl load dishes=$dishes,tags=$setTagsDB")
                localDataSource.insertDishesTagAndCrossRef(dishesDB, setTagsDB.toList().map {
                    TagDB(it)
                }, listDishTagCrossRefDB)
            }
            val dishesDB =
                localDataSource.getTagswithDishes(tag).firstOrNull()?.dishesDB ?: emptyList()
            val resDishes = dishesDB.map { dishDB ->
                Dish(
                    dishDB.id,
                    dishDB.name,
                    dishDB.price,
                    dishDB.weight,
                    dishDB.description,
                    dishDB.imageUrl
                )
            }
            _dishes.value = ResultData.Success(resDishes)

            if (!useCash) {
                val resTags = localDataSource.getTags().map {
                    Tag(it.name)
                }
                _tags.value = ResultData.Success(resTags)
            }
        } catch (e: Exception) {
            _dishes.value = ResultData.Error(e.message ?: "error load dishes[${e.javaClass.name}]")
        }
    }
}