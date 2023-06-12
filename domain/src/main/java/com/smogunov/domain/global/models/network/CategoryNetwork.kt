package com.smogunov.domain.global.models.network

import com.google.gson.annotations.SerializedName


data class CategoryNetworkItem(
    val id: Int?,
    val name: String?,
    val image_url: String?
)

data class CategoryNetwork(
    @SerializedName("сategories")
    val categories: List<CategoryNetworkItem>
)