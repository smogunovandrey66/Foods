package com.smogunov.foods.data.datasource

import android.util.Log
import com.smogunov.domain.global.models.database.CartItemDB
import com.smogunov.domain.global.models.database.CategoryDB
import com.smogunov.domain.global.models.database.DishDB
import com.smogunov.domain.global.models.database.DishTagCrossRefDB
import com.smogunov.domain.global.models.database.DishWithCartItemDB
import com.smogunov.domain.global.models.database.TagDB
import com.smogunov.domain.global.models.database.TagWithDishesDB
import com.smogunov.domain.global.utils.log
import com.smogunov.foods.data.database.FoodDataBase

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

    override suspend fun insertDishesTagAndCrossRef(
        dishes: List<DishDB>,
        tags: List<TagDB>,
        crossRefDB: List<DishTagCrossRefDB>
    ) {
        Log.d("GLOBAL_TAG_LOG", "LocalDataSourceImpl insertDishesAndTag dishes=$dishes,tags=$tags")
        dataBase.dishDao().clearTags()
        dataBase.dishDao().insertTags(tags)

        dataBase.dishDao().clearDishes()
        dataBase.dishDao().insertDishes(dishes)

        dataBase.dishDao().clearDishTagRef()
        dataBase.dishDao().insertDishTagRef(crossRefDB)
    }

    override suspend fun getTags(): List<TagDB> = dataBase.dishDao().getAllTags()

    override suspend fun getAllCartItems(): List<DishWithCartItemDB> {
        log("LocalDataSourceImpl getAllCartItems before")
        val cartItemDB = dataBase.cartDao().getCartItems()
        for (item in cartItemDB) {
            log("LocalDataSourceImpl getAllCartItems item=$item")
        }
        log("LocalDataSourceImpl getAllCartItems cartItemDB=$cartItemDB")
        return cartItemDB
    }

    override suspend fun changeCartItem(idDish: Int, count: Int) {
        val cartItemDB = dataBase.cartDao().getCartItem(idDish)
        log("LocalDataSourceImpl changeCartItem cartItemDB=$cartItemDB,count=$count")

        //Уже есть запись
        if (cartItemDB != null) {
            //Добавляем
            if (count > 0) {
                val newCartItemDB = CartItemDB(cartItemDB.idCart, idDish, cartItemDB.count + count)
                dataBase.cartDao().updateCartItem(newCartItemDB)
                log("LocalDataSourceImpl changeCartItem update cartItem(add)")
            } else {
                //Новое уменьшенное значение
                val newCountDishes = cartItemDB.count + count
                //Уменьшаем до адекватного значения
                if (newCountDishes > 0) {
                    val newCartItemDB = CartItemDB(cartItemDB.idCart, idDish, newCountDishes)
                    dataBase.cartDao().updateCartItem(newCartItemDB)
                    log("LocalDataSourceImpl changeCartItem update cartItem(remove)")
                } else {
                    //Удаляем
                    dataBase.cartDao().deleteCartItem(cartItemDB)
                    log("LocalDataSourceImpl changeCartItem remove")
                }
            }
        } else { //Записи нет
            //И изменяем в сторону увеличения
            if (count > 0) {
                val newCartItemDB = CartItemDB(0, idDish, count)
                dataBase.cartDao().addCartItem(newCartItemDB)
                log("LocalDataSourceImpl changeCartItem add")
            }
        }
    }
}