package es.unex.gps.weathevent

import android.app.Application
import es.unex.gps.weathevent.util.AppContainer

class WeathApplication : Application() {
    lateinit var appContainer: AppContainer

    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer(this)
    }
}