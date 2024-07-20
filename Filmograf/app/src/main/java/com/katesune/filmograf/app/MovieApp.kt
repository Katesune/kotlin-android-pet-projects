package com.katesune.filmograf.app

import android.app.Application
import com.katesune.filmograf.di.appModule
import com.katesune.filmograf.di.dataModule
import com.katesune.filmograf.di.domainModule
import com.katesune.filmograf.di.retrofitModule
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