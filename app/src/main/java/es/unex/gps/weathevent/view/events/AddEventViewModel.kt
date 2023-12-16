package es.unex.gps.weathevent.view.events

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import es.unex.gps.weathevent.WeathApplication
import es.unex.gps.weathevent.data.repositories.CiudadesRepository
import es.unex.gps.weathevent.data.repositories.EventsRepository
import es.unex.gps.weathevent.model.Ciudad
import es.unex.gps.weathevent.model.Event
import es.unex.gps.weathevent.model.Fecha
import kotlinx.coroutines.launch


class AddEventViewModel (
    private val ciudadesRepository: CiudadesRepository,
    private val eventsRepository: EventsRepository
): ViewModel() {
    val ciudades = ciudadesRepository.ciudades
    var ciudad = MutableLiveData<Ciudad>(null)

    fun validateName(name: String): String? {
        return if (name.length < 3) {
            "Se debe indicar un nombre de al menos tres caracteres\n"
        } else null
    }

    fun validateFecha(fecha: Fecha): String? {
        return if (!fecha.isValid()) {
            "Debe introducir una fecha y una hora\n"
        } else null
    }

    suspend fun filterMunicipio(municipio: String): String? {
        ciudad.value = ciudadesRepository.getCiudad(municipio)

        return if (ciudad.value != null) null
        else "Revise la ortografía del municipio\n"
    }

    fun insertEvent(name: String, fecha: Fecha): Boolean {
        return if (ciudad.value != null) {
            val event = Event(null, name, ciudad.value!!.name, fecha, -1, ciudad.value!!.ciudadId)

            viewModelScope.launch {
                eventsRepository.addEvent(event)
            }

            true
        } else false
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

                return AddEventViewModel(
                    (application as WeathApplication).appContainer.ciudadesRepository,
                    application.appContainer.eventsRepository
                ) as T
            }
        }
    }
}