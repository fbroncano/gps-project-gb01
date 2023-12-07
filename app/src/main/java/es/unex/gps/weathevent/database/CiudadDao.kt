package es.unex.gps.weathevent.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import es.unex.gps.weathevent.model.Ciudad

@Dao
interface CiudadDao {
    @Query("SELECT * FROM Ciudad")
    fun getCiudades() : LiveData<List<Ciudad>>

    @Query("SELECT count(*) FROM Ciudad")
    suspend fun getNumberOfCiudades() : Long

    @Query("SELECT * FROM Ciudad WHERE ciudadId = :ciudadId")
    suspend fun getCiudad(ciudadId: Long) : Ciudad

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(ciudades : List<Ciudad>)
}