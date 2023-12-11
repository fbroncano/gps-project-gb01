package es.unex.gps.weathevent.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import es.unex.gps.weathevent.WeathApplication
import es.unex.gps.weathevent.data.repositories.CiudadesRepository
import es.unex.gps.weathevent.data.repositories.FavoritosRepository
import es.unex.gps.weathevent.data.repositories.UserRepository
import es.unex.gps.weathevent.model.Ciudad
import es.unex.gps.weathevent.model.User
import kotlinx.coroutines.launch

class FavoritosViewModel(
private val favoritosRepository: FavoritosRepository
): ViewModel() {

    val favorites = favoritosRepository.favs

    private val _toast = MutableLiveData<String?>()
    val toast: LiveData<String?>
    get() = _toast

    var user: User? = null
        set(value) {
            field = value
            favoritosRepository.setUserid(value!!.userId!!)
        }

    fun setNoFavorite(ciudad: Ciudad){
        viewModelScope.launch {
            favoritosRepository.desmarkFavorite(user?.userId!!, ciudad.ciudadId)
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

                return FavoritosViewModel(
                    (application as WeathApplication).appContainer.favoritosRepository) as T
            }
        }
    }
}