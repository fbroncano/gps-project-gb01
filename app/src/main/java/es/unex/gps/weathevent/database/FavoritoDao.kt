package es.unex.gps.weathevent.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import es.unex.gps.weathevent.model.Ciudad
import es.unex.gps.weathevent.model.Favorito

@Dao
interface FavoritoDao {

    @Query("SELECT * FROM Ciudad WHERE ciudadId IN (SELECT ciudadId FROM Favorito WHERE userId = :userId)")
    fun getCiudadUser(userId: Long) : LiveData<List<Ciudad>>

    @Query("SELECT count(*) FROM Favorito WHERE userId = :userId AND  ciudadId = :ciudadId")
    suspend fun getFavorito(userId: Long, ciudadId: Long) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorito(favorito: Favorito)

    @Delete
    suspend fun deleteFavorito(favorito: Favorito)
}