package com.katesune.cryptoapp.app

import android.app.Application
import com.katesune.cryptoapp.di.appModule
import com.katesune.cryptoapp.di.dataModule
import com.katesune.cryptoapp.di.domainModule
import com.katesune.cryptoapp.di.retrofitModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class CryptoApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(level = Level.ERROR)
            androidContext(this@CryptoApp)
            modules(listOf(retrofitModule, appModule, domainModule, dataModule))
        }
    }

}