package com.example.filmograf.app

import android.app.Application
import com.example.filmograf.di.appModule
import com.example.filmograf.di.dataModule
import com.example.filmograf.di.domainModule
import com.example.filmograf.di.retrofitModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MovieApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(level = Level.ERROR)
            androidContext(this@MovieApp)
            modules(listOf(retrofitModule, appModule, domainModule, dataModule))
        }
    }

}