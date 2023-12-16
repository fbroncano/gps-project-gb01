package es.unex.gps.weathevent.view.events

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewmodel.CreationExtras
import es.unex.gps.weathevent.WeathApplication
import es.unex.gps.weathevent.api.getElTiempoService
import es.unex.gps.weathevent.data.api.HumedadRelativa
import es.unex.gps.weathevent.data.api.ProximosDias
import es.unex.gps.weathevent.data.api.ProximosDiasArray
import es.unex.gps.weathevent.data.api.ProximosDiasSingle
import es.unex.gps.weathevent.data.api.SensTermica
import es.unex.gps.weathevent.data.api.Temperatura
import es.unex.gps.weathevent.data.api.attributes
import es.unex.gps.weathevent.data.repositories.EventsRepository
import es.unex.gps.weathevent.data.repositories.PronosticoRepository
import es.unex.gps.weathevent.model.Event
import es.unex.gps.weathevent.model.Fecha
import java.time.LocalDateTime

class EventDetailsViewModel (
    private val eventsRepository: EventsRepository,
    private val pronosticoRepository: PronosticoRepository
) : ViewModel() {

    val eventId = MutableLiveData(-1L)
    val eventLiveData : LiveData<Event>
        get() = eventId.switchMap { eventId ->
            Log.d("EventDetail", "Buscando el id $eventId")
            eventsRepository.getEvent(eventId)
        }

    var event: Event? = null
    var proximoDia : ProximosDias? = null
    var pronostico = false

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun loadWeatherEvent(event: Event) {
        val time = LocalDateTime.now()
        val date = Fecha(
            time.dayOfMonth,
            time.monthValue,
            time.year,
            time.hour,
            time.minute
        ).getAbsoluteDay()

        // Si la fecha del evento es el actual dia o el siguiente, se manda el Pronostico
        if (date == event.date.getAbsoluteDay() || date + 1 == event.date.getAbsoluteDay()) {
            // Comprobar si hay tiempo para esa hora
            val municipioResponse = pronosticoRepository.getMunicipioResponse(event.locationId)
            val horasManana = municipioResponse.pronostico?.manana?.viento?.map {
                it.attributes?.periodo
            }

            val strHora: String =
                if (event.date.hora < 10) "0${event.date.hora}" else event.date.hora.toString()

            if (horasManana?.indexOf(strHora) != -1) {
                proximoDia = sendPronosticoData(event)
                pronostico = true
            } else {
                proximoDia = sendWeatherData(event)
                pronostico = false
                Log.d("APIResult", proximoDia.toString())
            }
        } else {
            // Si no se tienen los datos de pronóstico, se manda predicción
            proximoDia = sendWeatherData(event)
            pronostico = false
            Log.d("APIResult", proximoDia.toString())
        }

    }

    private suspend fun sendWeatherData(event: Event): ProximosDias? {
        var proximoDia: ProximosDias? = null
        var locationId = event.locationId.toString()

        if (locationId.length == 4) locationId = "0$locationId"

        val codprov = locationId.subSequence(0, locationId.length - 3).toString()
        val municipioResponse = getElTiempoService().getMunicipio(codprov, locationId)
        municipioResponse.obtainProximosDias()

        // Comprobar el objeto ProximoDia
        for (obj in municipioResponse.proximosDias) {
            if (obj is ProximosDiasArray) {
                val dates = obj.attributes?.fecha?.split("-")
                val fecha = Fecha(dates?.get(2)?.toInt()!!, dates[1].toInt(), dates[0].toInt(), 0, 0)

                Log.d("DatesCast", "" + fecha.getAbsoluteDay() + "  " + event.date.getAbsoluteDay())
                if (fecha.getAbsoluteDay() == event.date.getAbsoluteDay()) {
                    proximoDia = obj
                }
            } else if (obj is ProximosDiasSingle) {
                val dates = obj.attributes?.fecha?.split("-")
                val fecha = Fecha(dates?.get(2)?.toInt()!!, dates[1].toInt(), dates[0].toInt(), 0, 0)

                if (fecha.getAbsoluteDay() == event.date.getAbsoluteDay()) {
                    proximoDia = obj
                }
            }
        }

        return proximoDia
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private suspend fun sendPronosticoData(event: Event) : ProximosDias? {
        var proximo: ProximosDias? = null
        var locationId = event.locationId.toString()
        val time = LocalDateTime.now()
        val date = Fecha(time.dayOfMonth, time.monthValue, time.year, time.hour, time.minute).getAbsoluteDay()

        if (locationId.length == 4) locationId = "0$locationId"

        val codprov = locationId.subSequence(0, locationId.length - 3).toString()
        val municipioResponse = getElTiempoService().getMunicipio(codprov, locationId)

        val horasHoy = municipioResponse.pronostico?.hoy?.viento?.map {
            it.attributes?.periodo
        }

        val horasManana = municipioResponse.pronostico?.manana?.viento?.map {
            it.attributes?.periodo
        }

        if (date == event.date.getAbsoluteDay()) {
            val strHora : String = if (event.date.hora < 10) "0${event.date.hora}" else event.date.hora.toString()
            val index = horasHoy?.indexOf(strHora)
            val hoy = municipioResponse.pronostico?.hoy

            if (index != -1) {
                proximo = ProximosDiasSingle(
                    attributes(horasHoy?.get(index!!), null, null, null),
                    hoy?.precipitacion?.get(index!!),
                    hoy?.estadoCielo?.get(index!!),
                    hoy?.viento?.get(index!!),
                    Temperatura(hoy?.temperatura?.get(index!!)),
                    SensTermica(hoy?.sensTermica?.get(index!!)),
                    HumedadRelativa(null, hoy?.humedadRelativa?.get(index!!)),
                    "No hay datos",
                    hoy?.estadoCieloDescripcion?.get(index!!)
                )
            }

        } else if (date + 1 == event.date.getAbsoluteDay()) {
            val strHora : String = if (event.date.hora < 10) "0${event.date.hora}" else event.date.hora.toString()
            val index = horasManana?.indexOf(strHora)
            val manana = municipioResponse.pronostico?.manana

            Log.d("INDICES", index.toString())
            if (index != -1) {
                proximo = ProximosDiasSingle(
                    attributes(horasManana?.get(index!!), null, null, null),
                    manana?.precipitacion?.get(index!!),
                    manana?.estadoCielo?.get(index!!),
                    manana?.viento?.get(index!!),
                    Temperatura(manana?.temperatura?.get(index!!)),
                    SensTermica(manana?.sensTermica?.get(index!!)),
                    HumedadRelativa(null, manana?.humedadRelativa?.get(index!!)),
                    "No hay datos",
                    manana?.estadoCieloDescripcion?.get(index!!)
                )
            }
        }

        return proximo
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

                return EventDetailsViewModel(
                    (application as WeathApplication).appContainer.eventsRepository,
                    application.appContainer.pronosticoRepository
                ) as T
            }
        }
    }

}