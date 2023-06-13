package com.smogunov.domain.global.repositories

import com.smogunov.domain.global.resultdata.ResultData
import kotlinx.coroutines.flow.StateFlow

interface CartRepository {
    val cartItems: StateFlow<ResultData>
    suspend fun loadCartItems()

    val resultChangeCartItem: StateFlow<ResultData>
    suspend fun changeCartItem(idDish: Int, count: Int)
}