package es.unex.gps.weathevent.view.buscar

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import es.unex.gps.weathevent.WeathApplication
import es.unex.gps.weathevent.api.APIError
import es.unex.gps.weathevent.data.repositories.CiudadesRepository
import es.unex.gps.weathevent.data.repositories.FavoritosRepository
import es.unex.gps.weathevent.data.repositories.UserRepository
import es.unex.gps.weathevent.model.Ciudad
import es.unex.gps.weathevent.model.User
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class BuscarViewModel(
    private val ciudadesRepository: CiudadesRepository,
    private val favoritosRepository: FavoritosRepository,
): ViewModel() {

    val ciudades = ciudadesRepository.ciudades
    val favorites = favoritosRepository.favs
    var query = MutableLiveData("")

    val ciudadesFiltered: LiveData<List<Ciudad>>
        get() = query.switchMap { query ->
                ciudades.map { ciudades ->
                    ciudades.filter { ciudad ->
                        ciudad.name.contains(query, ignoreCase = true)
                    }
                }
            }

    private val _spinner = MutableLiveData<Boolean>()
    val spinner: LiveData<Boolean>
        get() = _spinner

    private val _toast = MutableLiveData<String?>()
    val toast: LiveData<String?>
        get() = _toast

    init{
        refresh()
    }

    suspend fun changeFavorite(ciudad: Ciudad): Boolean {
        Log.d("Favorite", "${favoritosRepository.checkFavorite(ciudad.ciudadId)}")

        if (favoritosRepository.checkFavorite(ciudad.ciudadId)) {
            Log.d("DelFavorite", "${ciudad.ciudadId} se ha eliminado a favoritos")
            favoritosRepository.desmarkFavorite(ciudad.ciudadId)
            return false
        } else {
            Log.d("AddFavorite", "${ciudad.ciudadId} se ha añadido a favoritos")
            favoritosRepository.markFavorite(ciudad.ciudadId)
            return true
        }
    }

    fun checkFavorite(ciudad: Ciudad) : Boolean {
        return favorites.value?.contains(ciudad) == true
    }

    private fun refresh() {
        launchDataLoad {
            ciudadesRepository.tryUpdateRecentCiudadesCache()
        }
    }

    fun onToastShown() {
        _toast.value = null
    }

    private fun launchDataLoad(block: suspend () -> Unit): Job {
        return viewModelScope.launch {
            try {
                _spinner.value = true
                block()
            } catch (error: APIError) {
                _toast.value = error.message
            } finally {
                _spinner.value = false
            }
        }
    }

    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Get the Application object from extras
                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])

                return BuscarViewModel(
                    (application as WeathApplication).appContainer.ciudadesRepository,
                    application.appContainer.favoritosRepository
                ) as T
            }
        }
    }
}