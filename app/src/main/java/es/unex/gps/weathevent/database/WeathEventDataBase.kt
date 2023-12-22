package es.unex.gps.weathevent.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import es.unex.gps.weathevent.model.Ciudad
import es.unex.gps.weathevent.model.User
import es.unex.gps.weathevent.model.UserCiudadCrossRef
import es.unex.gps.weathevent.model.Event

<<<<<<< HEAD


=======
@Database(version = 5, entities = [User::class, Ciudad::class, Event::class, UserCiudadCrossRef::class], exportSchema = false)
>>>>>>> origin/develop
abstract class WeathEventDataBase : RoomDatabase() {

}
