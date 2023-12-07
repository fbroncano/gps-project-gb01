package es.unex.gps.weathevent.data.repositories

import androidx.lifecycle.map
import es.unex.gps.weathevent.database.CiudadDao
import es.unex.gps.weathevent.database.FavoritoDao
import es.unex.gps.weathevent.model.Ciudad
import es.unex.gps.weathevent.model.Favorito
import es.unex.gps.weathevent.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext

class FavoritosRepository private constructor(
    private val favoritoDao: FavoritoDao
) {

    var userId: Long? = null

    val favs = favoritoDao.getCiudadUser(userId!!)

    fun setUser(user: User) {
        userId = user.userId
    }
    suspend fun markFavorite(ciudadId: Long) {
        favoritoDao.insertFavorito(Favorito(userId!!, ciudadId))
    }

    suspend fun desmarkFavorite(ciudadId: Long) {
        favoritoDao.deleteFavorito(Favorito(userId!!, ciudadId))
    }

    suspend fun checkFavorite(ciudadId: Long) : Boolean {
        return favoritoDao.getFavorito(userId!!, ciudadId) != 1L
    }

    companion object {
        @Volatile
        private var INSTANCE: FavoritosRepository? = null

        fun getInstance(favoritoDao: FavoritoDao): FavoritosRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: FavoritosRepository(favoritoDao).also {
                    INSTANCE = it
                }
            }
        }
    }
}