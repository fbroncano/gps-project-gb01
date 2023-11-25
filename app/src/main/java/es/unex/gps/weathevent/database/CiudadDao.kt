package es.unex.gps.weathevent.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import es.unex.gps.weathevent.Model.Ciudad
import es.unex.gps.weathevent.model.Ciudad
import es.unex.gps.weathevent.model.UserCiudadCrossRef
import es.unex.gps.weathevent.model.UserWithCiudades

@Dao
interface CiudadDao {

    @Query("SELECT * FROM Ciudad WHERE ciudadId = :id")
    suspend fun findById(id: Long): Ciudad
    @Insert(onConflict = OnConflictStrategy.REPLACE) //En caso de que se intente guardar una tupla idéntica se reemplaza
    suspend fun insert(ciudad: Ciudad)
    @Delete
    suspend fun delete(ciudad: Ciudad)
    @Transaction
    @Query("SELECT * FROM User where userId = :userId") //A partir de un usuario nos devuelve todos sus ciudads
    suspend fun getUserWithCiudades(userId: Long): UserWithCiudades
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUserCiudad(crossRef: UserCiudadCrossRef)
    @Transaction
    suspend fun insertAndRelate(ciudad: Ciudad, userId: Long) {
        insert(ciudad)
        insertUserCiudad(UserCiudadCrossRef(userId, ciudad.ciudadId))
    }
}