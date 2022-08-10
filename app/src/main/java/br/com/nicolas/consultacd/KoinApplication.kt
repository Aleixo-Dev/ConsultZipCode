package br.com.nicolas.consultacd

import android.app.Application
import br.com.nicolas.consultacd.di.instance
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class KoinApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@KoinApplication)
            modules(instance)
        }
    }
}