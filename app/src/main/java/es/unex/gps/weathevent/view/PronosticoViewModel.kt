package es.unex.gps.weathevent.view

import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import es.unex.gps.weathevent.R
import es.unex.gps.weathevent.WeathApplication
import es.unex.gps.weathevent.view.weather.ProximasHorasAdapter
import es.unex.gps.weathevent.view.weather.ProximosDiasAdapter
import es.unex.gps.weathevent.api.APIError
import es.unex.gps.weathevent.api.APIHelpers
import es.unex.gps.weathevent.data.api.ProximosDiasArray
import es.unex.gps.weathevent.data.api.ProximosDiasSingle
import es.unex.gps.weathevent.data.repositories.FavoritosRepository
import es.unex.gps.weathevent.data.repositories.PronosticoRepository
import es.unex.gps.weathevent.databinding.ActivityPronosticoBinding
import es.unex.gps.weathevent.model.Ciudad
import es.unex.gps.weathevent.model.Fecha
import es.unex.gps.weathevent.model.ProximosDiasTiempo
import es.unex.gps.weathevent.model.TiempoPorHora
import kotlinx.coroutines.launch
import java.time.LocalDateTime


class PronosticoViewModel(
    private val favoritosRepository: FavoritosRepository,
    private val pronosticoRepository: PronosticoRepository
): ViewModel()  {

    val _ciudad = MutableLiveData<Ciudad?>()
    val ciudad: LiveData<Ciudad?>
        get() = _ciudad

    lateinit var binding : ActivityPronosticoBinding

    private val _tiempos = MutableLiveData<MutableList<TiempoPorHora>>()
    val tiempos: LiveData<MutableList<TiempoPorHora>>
        get() = _tiempos

    private val _dias = MutableLiveData<MutableList<ProximosDiasTiempo>>()
    val dias: LiveData<MutableList<ProximosDiasTiempo>>
        get() = _dias

    @RequiresApi(Build.VERSION_CODES.O)
    fun setUiViews() {
        viewModelScope.launch {
            if (ciudad.value?.ciudadId != null) {

                val response = pronosticoRepository.getMunicipioResponse(ciudad.value!!.ciudadId)

                binding.municipioView.text = ciudad.value?.name
                binding.descripcionView.text = pronosticoRepository.getStateSky(response)
                binding.temperatureView.text = pronosticoRepository.getTemperature(response)
            } else {
                binding.municipioView.text = "No hay municipio a mostrar"
            }
        }
    }

    fun ciudadBinding(context: Context) {

        viewModelScope.launch {
            val isFavorite = favoritosRepository.checkFavorite(ciudad.value!!.ciudadId)

            if (!isFavorite) {
                binding.imageFav.setImageResource(R.drawable.baseline_favorite_border_40)
            } else {
                binding.imageFav.setImageResource(R.drawable.baseline_favorite_40_red)
            }

            binding.imageFav.setOnClickListener {
                viewModelScope.launch {
                    val isFavorite = favoritosRepository.checkFavorite(ciudad.value!!.ciudadId)

                    if (!isFavorite) {
                        favoritosRepository.markFavorite(ciudad.value!!.ciudadId)
                        binding.imageFav.setImageResource(R.drawable.baseline_favorite_40_red)
                        Toast.makeText(
                            context,
                            "${ciudad.value?.name} añadido a favoritos",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        favoritosRepository.desmarkFavorite(ciudad.value!!.ciudadId)
                        binding.imageFav.setImageResource(R.drawable.baseline_favorite_border_40)

                        Toast.makeText(context,
                            "${ciudad.value?.name} eliminado de favoritos",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getProximasHoras(context: Context, adapter: ProximasHorasAdapter){
        if(_tiempos.value!!.isEmpty()) {
            try {
                Log.d("", "${ciudad.value?.name}")
                val response = pronosticoRepository.getMunicipioResponse(ciudad.value!!.ciudadId)

                val horasHoy = response.pronostico?.hoy?.viento?.map {
                    it.attributes?.periodo
                }

                var hour = LocalDateTime.now().hour.toString()
                if (hour.length == 1) hour = "0$hour"

                val index = horasHoy?.indexOf(hour)!!

                // Se añaden las horas restantes del día en curso
                if (index != -1) {
                    if ((index + 1) < horasHoy?.size!!) {
                        for (i in (index + 1)..<horasHoy?.size!!) {
                            _tiempos.value!!.add(
                                TiempoPorHora(
                                    horasHoy?.get(i)!! + ":00",
                                    APIHelpers.convertTempToPreferences(
                                        response.pronostico?.hoy?.temperatura?.get(
                                            i
                                        )!!.toLong(), context
                                    ),
                                    response.pronostico?.hoy?.estadoCieloDescripcion?.get(i)!!
                                )
                            )
                        }
                    }
                }

                // Se añaden las horas del día siguiente
                val horasManana = response.pronostico?.manana?.viento?.map {
                    it.attributes?.periodo
                }

                Log.d(
                    "INDICES",
                    index.toString() + " " + horasHoy?.size.toString() + " " + horasManana?.size.toString()
                )
                for (i in 0..<horasManana?.size!!) {
                    //TODO: Comprobar insercion de values
                    _tiempos.value!!.add(
                        TiempoPorHora(
                            horasManana?.get(i)!! + ":00",
                            APIHelpers.convertTempToPreferences(
                                response.pronostico?.manana?.temperatura?.get(
                                    i
                                )!!.toLong(), context
                            ),
                            response.pronostico?.manana?.estadoCieloDescripcion?.get(i)!!
                        )
                    )
                }

                adapter.updateData(_tiempos.value!!)
            } catch (error: APIError) {
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
            }
        }else{
            adapter.updateData(_tiempos.value!!)
        }
    }

    suspend fun getProximosDias(context: Context, adapter: ProximosDiasAdapter){
            if (_dias.value!!.isEmpty()) {
                try {
                    val response = pronosticoRepository.getMunicipioResponse(ciudad.value!!.ciudadId)
                    response.obtainProximosDias()

                    for (obj in response.proximosDias) {
                        if (obj is ProximosDiasArray) {
                            val dates = obj.attributes?.fecha?.split("-")
                            val fecha = Fecha(dates?.get(2)?.toInt()!!, dates?.get(1)?.toInt()!!, dates?.get(0)?.toInt()!!, 0, 0)

                            _dias.value!!.add(
                                ProximosDiasTiempo(
                                    fecha.getFormatDay(),
                                    "${APIHelpers.convertTempToPreferences(obj.temperatura?.minima?.toLong()!!, context)}\n${APIHelpers.convertTempToPreferences(obj.temperatura?.maxima?.toLong()!!, context)}",
                                    obj.estadoCieloDescripcion?.get(0)!!,
                                    "Sens. ter.: ${APIHelpers.convertTempToPreferences(obj.sensTermica?.minima?.toLong()!!, context)} - ${APIHelpers.convertTempToPreferences(obj.sensTermica?.maxima?.toLong()!!, context)}",
                                    "Precipitacion: ${obj.probPrecipitacion?.get(0)!!}%",
                                    "Viento: ${APIHelpers.convertVelToPreferences(obj.viento?.get(0)?.velocidad?.toLong()!!, context)} ${obj.viento?.get(0)?.direccion}"
                                )
                            )
                        } else if (obj is ProximosDiasSingle) {
                            val dates = obj.attributes?.fecha?.split("-")
                            val fecha = Fecha(dates?.get(2)?.toInt()!!, dates?.get(1)?.toInt()!!, dates?.get(0)?.toInt()!!, 0, 0)

                            _dias.value!!.add(
                                ProximosDiasTiempo(
                                    fecha.getFormatDay(),
                                    "${APIHelpers.convertTempToPreferences(obj.temperatura?.minima?.toLong()!!, context)}\n${APIHelpers.convertTempToPreferences(obj.temperatura?.maxima?.toLong()!!, context)}",
                                    obj.estadoCieloDescripcion!!,
                                    "Sens. ter.: ${APIHelpers.convertTempToPreferences(obj.sensTermica?.minima?.toLong()!!, context)} - ${APIHelpers.convertTempToPreferences(obj.sensTermica?.maxima?.toLong()!!, context)}",
                                    "Precipitacion: ${obj.probPrecipitacion?.get(0)!!}%",
                                    "Viento: ${APIHelpers.convertVelToPreferences(obj.viento?.velocidad?.toLong()!!, context)} ${obj.viento?.direccion}"
                                )
                            )
                        }
                    }

                    Log.d("","hola ${_dias.value?.isEmpty()}")
                    adapter.updateData(_dias.value!!)
                } catch (error: APIError) {
                    Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
                }
            } else{
                adapter.updateData(_dias.value!!)
            }

    }

    init {
        _tiempos.value = mutableListOf()
        _dias.value = mutableListOf()
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

                return PronosticoViewModel(
                    (application as WeathApplication).appContainer.favoritosRepository,
                    application.appContainer.pronosticoRepository
                ) as T
            }
        }
    }
}