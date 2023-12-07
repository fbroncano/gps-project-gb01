package es.unex.gps.weathevent.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import es.unex.gps.weathevent.database.FavoritoDao
import es.unex.gps.weathevent.model.Ciudad
import es.unex.gps.weathevent.model.Favorito

class FavoritosRepository private constructor(
    private val favoritoDao: FavoritoDao
) {

    private val userFilter = MutableLiveData<Long>()

    val favs: LiveData<List<Ciudad>> =
        userFilter.switchMap{ userid -> favoritoDao.getCiudadUser(userid) }

    fun setUserid(userid: Long) {
        userFilter.value = userid
    }

    suspend fun markFavorite(userid: Long, ciudadId: Long) {
        favoritoDao.insertFavorito(Favorito(userid, ciudadId))
    }

    suspend fun desmarkFavorite(userid: Long, ciudadId: Long) {
        favoritoDao.deleteFavorito(Favorito(userid, ciudadId))
    }

    suspend fun checkFavorite(userid: Long, ciudadId: Long) : Boolean {
        return favoritoDao.getFavorito(userid, ciudadId) != 1L
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