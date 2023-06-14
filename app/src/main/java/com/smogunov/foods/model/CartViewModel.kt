package com.smogunov.foods.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.smogunov.domain.global.usecase.UseCaseCart
import kotlinx.coroutines.launch


/**
 * Модель для корзины
 */
class CartViewModel(private val useCaseCart: UseCaseCart) : ViewModel() {
    val cartItems = useCaseCart.cartItems
    val resultChangeCartItem = useCaseCart.resultChangeCartItem

    fun loadCartItems() {
        viewModelScope.launch {
            useCaseCart.loadCartItems()
        }
    }

    fun changeCartItem(idDish: Int, count: Int) {
        viewModelScope.launch {
            useCaseCart.changeCartItem(idDish, count)
        }
    }
}