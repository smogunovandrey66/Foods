package com.smogunov.domain.global.models

import android.app.ActivityManager.TaskDescription
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("dish")
data class Dish (
    @PrimaryKey
    val id: Int,
    val name: String,
    val price: Int,
    val weight: Int,
    val description: String,
    @ColumnInfo(name = "image_url")
    val imageUrl: String,
    val tags: List<String>
)