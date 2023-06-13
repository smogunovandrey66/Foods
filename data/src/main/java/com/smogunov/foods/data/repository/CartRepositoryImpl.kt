package com.smogunov.foods.data.repository

import androidx.room.util.convertUUIDToByte
import com.smogunov.domain.global.models.presentation.CartItem
import com.smogunov.domain.global.models.presentation.Dish
import com.smogunov.domain.global.repositories.CartRepository
import com.smogunov.domain.global.resultdata.ResultData
import com.smogunov.foods.data.datasource.LocalDataSource
import com.smogunov.foods.data.datasource.NetworkDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.lang.Exception

class CartRepositoryImpl(private val networkDataSource: NetworkDataSource, private val localDataSource: LocalDataSource): CartRepository {
    private val _cartItems: MutableStateFlow<ResultData> = MutableStateFlow(ResultData.Loading)
    private val _resultChangeCartItem: MutableStateFlow<ResultData> = MutableStateFlow(ResultData.Loading)

    override val cartItems: StateFlow<ResultData>
        get() = _cartItems

    override val resultChangeCartItem: StateFlow<ResultData>
        get() = _resultChangeCartItem

    override suspend fun loadCartItems() {
        try {
            _cartItems.value = ResultData.Loading
            val cartItems = localDataSource.getAllCartItems().map {dishWithCartItem ->
                dishWithCartItem.dishDB.let { dishDB->
                    CartItem(Dish(dishDB.id, dishDB.name, dishDB.price, dishDB.weight, dishDB.description, dishDB.imageUrl), dishWithCartItem.cartItem.count)
                }

            }
            _cartItems.value = ResultData.Success(cartItems)
        } catch (e: Exception) {
            _cartItems.value = ResultData.Error(e.message ?: "error load cartItems[${e.javaClass.name}]")
        }
    }

    override suspend fun changeCartItem(idDish: Int, count: Int) {
        try {
            localDataSource.changeCartItem(idDish, count)
            _resultChangeCartItem.value = ResultData.Success(true)
        } catch (e: Exception) {
            _resultChangeCartItem.value = ResultData.Error(e.message ?: "error change cartItem[${e.javaClass.name}]")
        }
    }
}