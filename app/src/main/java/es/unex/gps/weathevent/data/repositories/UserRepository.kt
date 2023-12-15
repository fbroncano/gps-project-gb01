package es.unex.gps.weathevent.data.repositories

import androidx.lifecycle.MutableLiveData
import es.unex.gps.weathevent.database.FavoritoDao
import es.unex.gps.weathevent.database.UserDao
import es.unex.gps.weathevent.model.User

class UserRepository private constructor(
    private val userDao: UserDao
) {
    suspend fun checkUserSesion(username: String, password: String): User? {
        return userDao.findByUsername(username, password)
    }

    suspend fun insertUser(user: User): Long {
        return userDao.insert(user)
    }

    companion object {
        @Volatile
        private var INSTANCE: UserRepository? = null
        fun getInstance(userDao: UserDao): UserRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: UserRepository(userDao).also {
                    INSTANCE = it
                }
            }
        }
    }
}