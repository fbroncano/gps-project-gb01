package es.unex.gps.weathevent.view.events

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import es.unex.gps.weathevent.WeathApplication
import es.unex.gps.weathevent.data.repositories.CiudadesRepository
import es.unex.gps.weathevent.data.repositories.EventsRepository
import es.unex.gps.weathevent.model.Ciudad
import es.unex.gps.weathevent.model.Event
import es.unex.gps.weathevent.model.Fecha
import kotlinx.coroutines.launch
import java.text.Normalizer


class AddEventViewModel (
    private val ciudadesRepository: CiudadesRepository,
    private val eventsRepository: EventsRepository
): ViewModel() {
    val ciudades = ciudadesRepository.ciudades
    var ciudad: Ciudad? = null
    private fun normalize(string: String): String {
        var str = Normalizer.normalize(string, Normalizer.Form.NFD)
        return str.replace("[^\\p{ASCII}]", "")
    }

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

    fun filterMunicipio(municipioName: String): String? {
        var errorMsg: String? = null
        var municipio = normalize(municipioName)

        val encontrados = ciudades.map {
            it.filter {
                normalize(it.name).contains(municipio, ignoreCase = true)
            }
        }.value

        // Filtramos si el municipio se ha encontrado
        Log.d("Ciudades", ciudadesRepository.toString())
        if (encontrados?.size == 1) {
            ciudad = encontrados[0]
        } else if (encontrados?.isEmpty() == true) {
            errorMsg = "Revise la ortografía o indique un municipio\n"
        } else if (encontrados?.size?: 0 > 2) {
            val identicos = encontrados?.filter {
                normalize(it.name).equals(municipio, ignoreCase = true)
            }

            if (identicos?.size == 1) {
                ciudad = encontrados[0]
            } else {
                errorMsg = "Debe concretar más el nombre del municipio\n"
            }
        }

        return errorMsg
    }

    fun insertEvent(name: String, fecha: Fecha): Boolean {
        return if (ciudad != null) {
            var event = Event(null, name, ciudad!!.name, fecha, -1, ciudad!!.ciudadId)
            viewModelScope.launch {
                eventsRepository.addEvent(event)
            }
            true
        } else {
            false
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

                return AddEventViewModel(
                    (application as WeathApplication).appContainer.ciudadesRepository,
                    application.appContainer.eventsRepository
                ) as T
            }
        }
    }
}