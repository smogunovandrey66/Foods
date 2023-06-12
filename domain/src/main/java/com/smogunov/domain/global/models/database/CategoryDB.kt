package com.smogunov.domain.global.models.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("categories")
data class CategoryDB(
    @PrimaryKey
    val id: Int,
    val name: String,
    @ColumnInfo("image_url")
    val image_url: String
)