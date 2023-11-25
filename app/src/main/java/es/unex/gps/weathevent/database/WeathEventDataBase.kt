package es.unex.gps.weathevent.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import es.unex.gps.weathevent.model.Ciudad
import es.unex.gps.weathevent.model.Event
import es.unex.gps.weathevent.model.User
import es.unex.gps.weathevent.model.UserCiudadCrossRef


abstract class WeathEventDataBase : RoomDatabase() {
    abstract fun userDao(): UserDao

}
