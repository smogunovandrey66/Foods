package com.smogunov.domain.global.usecase

import com.smogunov.domain.global.repositories.CartRepository
import com.smogunov.domain.global.resultdata.ResultData
import kotlinx.coroutines.flow.StateFlow

class UseCaseCart(private val cartRepository: CartRepository) {
    val cartItems = cartRepository.cartItems
    val resultChangeCartItem = cartRepository.resultChangeCartItem

    suspend fun loadCartItems() {
        cartRepository.loadCartItems()
    }
    suspend fun changeCartItem(idDish: Int, count: Int){
        cartRepository.changeCartItem(idDish, count)
    }
}