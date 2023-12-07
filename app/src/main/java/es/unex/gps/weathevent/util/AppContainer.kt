package es.unex.gps.weathevent.util

import android.content.Context
import es.unex.gps.weathevent.api.getElTiempoService
import es.unex.gps.weathevent.data.repositories.CiudadesRepository
import es.unex.gps.weathevent.data.repositories.EventsRepository
import es.unex.gps.weathevent.data.repositories.FavoritosRepository
import es.unex.gps.weathevent.database.WeathEventDataBase
import es.unex.gps.weathevent.model.User

class AppContainer(context: Context?) {
    private val networkService = getElTiempoService()
    private val db = WeathEventDataBase.getInstance(context!!)

    val favoritosRepository = FavoritosRepository.getInstance(db.favoritoDao())
    val eventsRepository = EventsRepository.getInstance(db.eventDao())
    val ciudadesRepository = CiudadesRepository.getInstance(db.ciudadDao(), networkService)

    fun setUser(user: User) {
        favoritosRepository.setUser(user)
        eventsRepository.setUser(user)
    }
}