package com.smogunov.domain.global.repositories

import com.smogunov.domain.global.resultdata.ResultData
import kotlinx.coroutines.flow.StateFlow

interface DishRepository {
    val dishes: StateFlow<ResultData>
    val tags: StateFlow<ResultData>

    suspend fun load(useCash: Boolean, tag: String)
}