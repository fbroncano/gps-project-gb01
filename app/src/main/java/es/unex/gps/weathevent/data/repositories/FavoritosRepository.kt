package es.unex.gps.weathevent.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import es.unex.gps.weathevent.database.FavoritoDao
import es.unex.gps.weathevent.model.Ciudad
import es.unex.gps.weathevent.model.Favorito
import es.unex.gps.weathevent.model.User

class FavoritosRepository private constructor(
    private val favoritoDao: FavoritoDao
) {

    val user = MutableLiveData<User>(null)

    val favs: LiveData<List<Ciudad>> =
        user.switchMap{
            favoritoDao.getCiudadUser(it.userId!!)
        }

    fun setUser(user: User) {
        this.user.value = user
    }

    suspend fun markFavorite(ciudadId: Long) {
        favoritoDao.insertFavorito(Favorito(user.value?.userId!!, ciudadId))
    }

    suspend fun desmarkFavorite(ciudadId: Long) {
        favoritoDao.deleteFavorito(Favorito(user.value?.userId!!, ciudadId))
    }

    suspend fun checkFavorite(ciudadId: Long) : Boolean {
        return favoritoDao.getFavorito(user.value?.userId!!, ciudadId) == 1L
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