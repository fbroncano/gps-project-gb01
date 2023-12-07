package es.unex.gps.weathevent.data.repositories

import es.unex.gps.weathevent.database.UserDao

class UserRepository private constructor(
    private val userDao: UserDao
) {
    suspend fun checkUserSesion(username: String, password: String) {
        userDao.findByUsername(username, password)
    }
}