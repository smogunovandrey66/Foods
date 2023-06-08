package com.smogunov.domain.global.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("category")
data class Category(
    @PrimaryKey
    val id: Int,
    val name: String,
    @ColumnInfo("image_url")
    val imageUrl: String
)