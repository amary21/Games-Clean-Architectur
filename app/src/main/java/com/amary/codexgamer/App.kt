package com.amary.codexgamer

import android.app.Application
import com.amary.codexgamer.core.di.databaseModule
import com.amary.codexgamer.core.di.networkModule
import com.amary.codexgamer.core.di.repositoryModule
import com.amary.codexgamer.di.useCaseModule
import com.amary.codexgamer.di.viewModelModule
import com.facebook.stetho.Stetho
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@App)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}