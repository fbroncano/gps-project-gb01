package es.unex.gps.weathevent.view.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import es.unex.gps.weathevent.WeathApplication
import es.unex.gps.weathevent.data.repositories.CiudadesRepository
import es.unex.gps.weathevent.data.repositories.FavoritosRepository
import es.unex.gps.weathevent.model.Ciudad
import kotlinx.coroutines.launch

class BuscarViewModel(
    private val ciudadesRepository: CiudadesRepository,
    private val favoritosRepository: FavoritosRepository
): ViewModel() {

    val ciudades = ciudadesRepository.ciudades
    val favorites = favoritosRepository.favs
    var query = ""


    fun setFavorite(ciudad: Ciudad) {
        viewModelScope.launch {
            favoritosRepository.markFavorite(ciudad.ciudadId)
        }
    }

    fun checkFavorite(ciudad: Ciudad) : Boolean {
        return favorites.value?.contains(ciudad) == true
    }

    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Get the Application object from extras
                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]) as WeathApplication

                return BuscarViewModel(
                    application.appContainer.ciudadesRepository,
                    application.appContainer.favoritosRepository
                ) as T
            }
        }
    }
}