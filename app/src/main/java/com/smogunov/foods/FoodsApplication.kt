package com.smogunov.foods

import android.app.Application
import com.smogunov.di.mainModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class FoodsApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@FoodsApplication)
            modules(mainModule)
        }
    }
}