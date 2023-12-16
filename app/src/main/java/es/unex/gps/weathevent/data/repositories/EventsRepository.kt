package es.unex.gps.weathevent.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import es.unex.gps.weathevent.database.EventDao
import es.unex.gps.weathevent.model.Ciudad
import es.unex.gps.weathevent.model.Event
import es.unex.gps.weathevent.model.User

class EventsRepository private constructor(
    private val eventDao: EventDao
) {
    val user = MutableLiveData<User>(null)

    val events: LiveData<List<Event>> =
        user.switchMap {
            eventDao.searchByUser(user.value?.userId!!)
        }

    fun setUser(user: User) {
        this.user.value = user
    }

    suspend fun addEvent(event: Event) {
        event.userid = user.value!!.userId!!
        eventDao.insertEvent(event)
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