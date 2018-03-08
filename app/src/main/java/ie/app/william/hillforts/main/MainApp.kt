package ie.app.william.hillforts.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import ie.app.william.hillforts.models.HillfortMemStore
import ie.app.william.hillforts.models.HillfortStore
import ie.app.william.hillforts.room.HillfortsStoreRoom

class MainApp : Application(), AnkoLogger {

    lateinit var hillforts: HillfortStore

    override fun onCreate() {
        super.onCreate()
        // placemarks = PlacemarkMemStore()
        hillforts = HillfortsStoreRoom (applicationContext)
        info("Hillforts started")
    }
}