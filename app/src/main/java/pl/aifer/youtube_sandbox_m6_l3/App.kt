package pl.aifer.youtube_sandbox_m6_l3

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import pl.aifer.youtube_sandbox_m6_l3.core.di.koinModules

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(koinModules)
        }
    }
}