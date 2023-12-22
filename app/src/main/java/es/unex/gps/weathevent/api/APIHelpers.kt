package es.unex.gps.weathevent.api

import android.content.Context
import android.util.Log
import androidx.preference.PreferenceManager
import es.unex.gps.weathevent.data.api.Municipio

class APIHelpers {
    val api = getElTiempoService()

    suspend fun fetchMunicipios() : List<Municipio> {
        try {
            val provinciasResponse = api.getProvincias()
            var municipios = arrayListOf<Municipio>()

            for (provincia in provinciasResponse.provincias) {
                if (provincia.CODPROV != null) {
                    val municipiosResponse = api.getMunicipios(provincia.CODPROV!!)
                    municipios.addAll(municipiosResponse.municipios)
                }
            }

            return municipios
        } catch (cause: APIError) {
            Log.wtf("APIHelpers", cause)
            return emptyList()
        } catch (cause : Exception) {
            Log.wtf("APIHelpers", cause)
            return emptyList()
        }
    }

    companion object {
        fun getCodProv(ciudadId: Long): String {
            return if (ciudadId < 10000) "0${ciudadId.toString()[0]}"
            else ciudadId.toString().subSequence(0, 2).toString()
        }

        fun getCiudadFormat(ciudadId: Long): String {
            return if (ciudadId < 10000) "0$ciudadId" else ciudadId.toString()
        }

        fun convertTempToPreferences(temperature : Long, context : Context) : String {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context).all
            val grados = preferences["Grados"] as String? ?: "Celsius"

            if (grados == "Fahrenheit") {
                val result = temperature.toFloat() * 1.8 + 32.0
                Log.d("ConvertTemperature", "${(result.toInt())}º F")
                return "${(result.toInt())}º F"
            } else {
                Log.d("ConvertTemperature", "${temperature}º C")
                return "${temperature}º C"
            }
        }

        fun convertVelToPreferences(velocidad : Long, context : Context) : String {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context).all
            val grados = preferences["Velocidad"] as String? ?: "Km/h"

            if (grados == "MPH") {
                val result = velocidad.toFloat() / 1.609
                Log.d("ConvertVelocidad", "${(result.toInt())}MPH")
                return "${(result.toInt())}MPH"
            } else {
                Log.d("ConvertVelocidad", "${velocidad}km/h")
                return "${velocidad}km/h"
            }
        }
    }
}