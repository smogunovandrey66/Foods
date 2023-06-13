package com.smogunov.foods.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.smogunov.domain.global.models.database.CartItemDB
import com.smogunov.domain.global.models.database.DishWithCartItemDB

@Dao
interface CartDao {
    @Insert
    suspend fun addCartItem(cartItemDB: CartItemDB)

    @Transaction
    @Query("SELECT dishes.*, carts.* FROM dishes, carts WHERE dishes.id = carts.id_dish")
    suspend fun getCartItems(): List<DishWithCartItemDB>

    @Query("SELECT * FROM carts WHERE id_dish = :idDish")
    suspend fun getCartItem(idDish: Int): CartItemDB?

    @Update
    suspend fun updateCartItem(cartItemDB: CartItemDB)

    @Delete
    suspend fun deleteCartItem(cartItemDB: CartItemDB)
}