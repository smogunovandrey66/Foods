package com.smogunov.di

import com.smogunov.foods.data.network.FoodApiService
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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