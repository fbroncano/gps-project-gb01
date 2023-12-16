package es.unex.gps.weathevent.view.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import es.unex.gps.weathevent.WeathApplication
import es.unex.gps.weathevent.data.repositories.FavoritosRepository
import es.unex.gps.weathevent.model.Ciudad
import kotlinx.coroutines.launch

class FavoritosViewModel(
    private val favoritosRepository: FavoritosRepository
): ViewModel() {

    private var favChange = MutableLiveData(0)

    val favorites: LiveData<List<Ciudad>>
        get() = favChange.switchMap {
            favoritosRepository.favs
        }

    fun setNoFavorite(ciudad: Ciudad) {
        viewModelScope.launch {
            favoritosRepository.desmarkFavorite(ciudad.ciudadId)
            favChange.value =+ 1
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
                    (application as WeathApplication).appContainer.favoritosRepository
                ) as T
            }
        }
    }
}