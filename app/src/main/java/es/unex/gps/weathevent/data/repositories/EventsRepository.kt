package es.unex.gps.weathevent.data.repositories

import es.unex.gps.weathevent.database.EventDao
import es.unex.gps.weathevent.model.User

class EventsRepository private constructor(
    private val eventDao: EventDao
) {
    var userId: Long? = null

    val events = eventDao.searchByUser(userId!!)

    fun setUser(user: User) {
        userId = user.userId
    }

    companion object {
        @Volatile
        private var INSTANCE: EventsRepository? = null

        fun getInstance(eventDao: EventDao): EventsRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: EventsRepository(eventDao).also {
                    INSTANCE = it
                }
            }
        }
    }
}