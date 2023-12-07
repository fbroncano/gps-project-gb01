package es.unex.gps.weathevent.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
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
    private val userRepository: UserRepository
): ViewModel() {

    val ciudades = ciudadesRepository.ciudades
    val favorites = favoritosRepository.favs
    var query = ""

    //TODO: Filtrado de la  recyclerView
    private val _filteredData = MediatorLiveData<List<Ciudad>>()
    val filteredData: LiveData<List<Ciudad>> get() = _filteredData
    private val addedSources = mutableListOf<LiveData<*>>()

    private val _spinner = MutableLiveData<Boolean>()
    val spinner: LiveData<Boolean>
        get() = _spinner

    private val _toast = MutableLiveData<String?>()
    val toast: LiveData<String?>
        get() = _toast

    init{
        refresh()
    }

    var user: User? = null

    fun setFavorite(ciudad: Ciudad) {
        viewModelScope.launch {
            favoritosRepository.markFavorite(user?.userId!!, ciudad.ciudadId)
        }
    }

    fun checkFavorite(ciudad: Ciudad) : Boolean {
        return favorites.value?.contains(ciudad) == true
    }

    private fun refresh() {
        launchDataLoad { ciudadesRepository.tryUpdateRecentCiudadesCache() }
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

    //TODO
    fun filterRecyclerView(text: String) {

        if (ciudades !in addedSources) {
            _filteredData.addSource(ciudades) { ciudadesList ->
                _filteredData.value = ciudadesList.filter { it.name.contains(text, ignoreCase = true) }
            }

            // Agrega la fuente a la lista
            addedSources.add(ciudades)
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
                    application.appContainer.favoritosRepository,
                    application.appContainer.userRepository
                ) as T
            }
        }
    }
}