package com.smogunov.foods

import android.app.Application
import com.smogunov.di.databaseModule
import com.smogunov.di.mainModule
import com.smogunov.di.networkModule
import com.smogunov.di.usecaseModule
import com.smogunov.foods.model.CartViewModel
import com.smogunov.foods.model.CategoriesViewModel
import com.smogunov.foods.model.DishesViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module

val presentationModule = module {
    single {
        DishesViewModel(get())
    }
    single {
        CategoriesViewModel(get())
    }
    single {
        CartViewModel(get())
    }
}

class FoodsApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@FoodsApplication)
            modules(mainModule, databaseModule, networkModule, usecaseModule, presentationModule)
        }
    }
}