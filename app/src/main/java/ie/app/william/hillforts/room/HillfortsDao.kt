package ie.app.william.hillforts.room

import android.arch.persistence.room.*
import ie.app.william.hillforts.models.HillfortModel

@Dao
interface HillfortsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun create(hillfort: HillfortModel)

    @Query("SELECT * FROM HillfortModel")
    fun findAll(): List<HillfortModel>

    @Update
    fun update(hillfort: HillfortModel)

    @Delete
    fun deleteHillfort(hillfort: HillfortModel)
}