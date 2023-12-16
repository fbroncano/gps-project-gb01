package es.unex.gps.weathevent.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import es.unex.gps.weathevent.model.User

@Dao
interface UserDao {
    @Query("SELECT * FROM user WHERE username = :username AND password = :password")
    suspend fun findByUsername(username: String, password: String): User

    @Query("SELECT * FROM user WHERE email = :first")
    suspend fun findByEmail(first: String): User

    @Query("SELECT COUNT(*) FROM user WHERE username = :username AND userId != :currentUserId")
    suspend fun availableUsername(username: String, currentUserId: Long?): Long

    @Insert
    suspend fun insert(user: User): Long

    @Update
    suspend fun updateUser(user: User)
}