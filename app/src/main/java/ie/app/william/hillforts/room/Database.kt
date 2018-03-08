package ie.app.william.hillforts.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import ie.app.william.hillforts.models.HillfortModel

@Database(entities = arrayOf(HillfortModel::class), version = 1)
abstract class Database : RoomDatabase() {

    abstract fun hillfortDao(): HillfortsDao
}