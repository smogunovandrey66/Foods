package com.smogunov.di

import com.google.gson.annotations.SerializedName
import com.smogunov.domain.global.models.database.CategoryDB
import com.smogunov.domain.global.models.network.CategoryNetwork
import com.smogunov.foods.data.network.FoodApiService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://run.mocky.io/v3/"

val networkModule = module {
    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    single {
        provideFoodApiService(get())
    }
}

fun provideFoodApiService(retrofit: Retrofit): FoodApiService =
    retrofit.create(FoodApiService::class.java)


fun main() = runBlocking {
    val retrorit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    val foodApiService = retrorit.create(FoodApiService::class.java)
    val dishes = foodApiService.getDishes()
    println(dishes)
}