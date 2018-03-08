package ie.app.william.hillforts.room

import android.arch.persistence.room.Room
import android.content.Context
import org.jetbrains.anko.coroutines.experimental.bg
import ie.app.william.hillforts.models.HillfortModel
import ie.app.william.hillforts.models.HillfortStore

class HillfortsStoreRoom(val context: Context) : HillfortStore {

    var dao: HillfortsDao

    init {
        val database = Room.databaseBuilder(context, Database::class.java, "room_sample.db")
                .fallbackToDestructiveMigration()
                .build()
        dao = database.hillfortDao()
    }

    override suspend fun findAll(): List<HillfortModel> {
        val deferredHillforts = bg {
            dao.findAll()
        }
        val placemarks = deferredHillforts.await()
        return placemarks
    }

    override fun create(hillfort: HillfortModel) {
        bg {
            dao.create(hillfort)
        }
    }

    override fun update(hillfort: HillfortModel) {
        bg {
            dao.update(hillfort)
        }
    }

    override fun delete(hillfort: HillfortModel) {
        bg {
            dao.deleteHillfort(hillfort)
        }
    }
}