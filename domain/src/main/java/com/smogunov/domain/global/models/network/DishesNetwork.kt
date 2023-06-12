package com.smogunov.domain.global.models.network

import com.google.gson.annotations.SerializedName


data class DishesNetworkItem(
    val id: Int?,
    val name: String?,
    val price: Int?,
    val weight: Int?,
    val description: String?,
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("tegs")
    val tags: List<String>?
)
data class DishesNetwork(
    val dishes: List<DishesNetworkItem>
)