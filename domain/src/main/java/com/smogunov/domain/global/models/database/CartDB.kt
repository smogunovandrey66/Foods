package com.smogunov.domain.global.models.database

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity("carts")
data class CartItemDB(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_cart")
    val idCart: Int,
    @ColumnInfo(name = "id_dish")
    val idDish: Int,
    val count: Int
)

data class DishWithCartItemDB(
    @Embedded
    val dishDB: DishDB,
    @Relation(parentColumn = "id", entityColumn = "id_dish")
    val cartItem: CartItemDB
)
