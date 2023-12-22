package es.unex.gps.weathevent.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import es.unex.gps.weathevent.model.Event

@Dao
interface EventDao {
    @Query("SELECT * FROM event WHERE userid = :userid ORDER BY ano, mes, dia, hora, mins")
    suspend fun searchByUser (userid: Long) : List<Event>

    @Query("SELECT * FROM event")
    suspend fun getAllEvents () : List<Event>
    @Insert
    suspend fun insertEvent (event: Event) : Long

    @Delete
    suspend fun deleteEvent (event: Event)

    @Update
    suspend fun updateEvent (event: Event)
}