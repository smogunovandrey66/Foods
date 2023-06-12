package com.smogunov.domain.global.resultdata

sealed class ResultData {
    class Success<T>(val value: T): ResultData()
    object Loading: ResultData()
    class Error(val message: String): ResultData()
}