package es.unex.gps.weathevent.api

import android.content.Context
import android.util.Log
import androidx.preference.PreferenceManager

class APIHelpers {
    val api = getElTiempoService()

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

            return if (grados == "Fahrenheit") {
                val result = temperature.toFloat() * 1.8 + 32.0
                Log.d("ConvertTemperature", "${(result.toInt())}º F")
                "${(result.toInt())}º F"
            } else {
                Log.d("ConvertTemperature", "${temperature}º C")
                "${temperature}º C"
            }
        }

        fun convertVelToPreferences(velocidad : Long, context : Context) : String {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context).all
            val grados = preferences["Velocidad"] as String? ?: "Km/h"

            return if (grados == "MPH") {
                val result = velocidad.toFloat() / 1.609
                Log.d("ConvertVelocidad", "${(result.toInt())}MPH")
                "${(result.toInt())}MPH"
            } else {
                Log.d("ConvertVelocidad", "${velocidad}km/h")
                "${velocidad}km/h"
            }
        }
    }
}