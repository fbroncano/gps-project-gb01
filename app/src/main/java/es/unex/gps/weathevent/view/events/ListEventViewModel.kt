package es.unex.gps.weathevent.view.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import es.unex.gps.weathevent.WeathApplication
import es.unex.gps.weathevent.data.repositories.CiudadesRepository
import es.unex.gps.weathevent.data.repositories.EventsRepository

class ListEventViewModel(
    private val eventsRepository: EventsRepository
) : ViewModel() {

    val events = eventsRepository.events
    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Get the Application object from extras
                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])

                return ListEventViewModel(
                    (application as WeathApplication).appContainer.eventsRepository
                ) as T
            }
        }
    }
}