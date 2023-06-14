package com.smogunov.domain.global.repositories

import com.smogunov.domain.global.resultdata.ResultData
import kotlinx.coroutines.flow.StateFlow

interface CategoryRepositories {
    val categories: StateFlow<ResultData>
    suspend fun load(useCash: Boolean)
}