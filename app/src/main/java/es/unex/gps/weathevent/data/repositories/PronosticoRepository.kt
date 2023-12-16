package es.unex.gps.weathevent.data.repositories

import android.content.Context
import es.unex.gps.weathevent.api.APIHelpers
import es.unex.gps.weathevent.api.getElTiempoService
import es.unex.gps.weathevent.data.api.MunicipioResponse
import es.unex.gps.weathevent.model.Ciudad


class PronosticoRepository(context: Context?) {
    val applicationContext = context!!.applicationContext


    suspend fun getMunicipioResponse(ciudadId: Long): MunicipioResponse {
        val codProv = APIHelpers.getCodProv(ciudadId)
        val ciudadId = APIHelpers.getCiudadFormat(ciudadId)
        return getElTiempoService().getMunicipio(codProv, ciudadId)
    }

    fun getStateSky(response: MunicipioResponse): String {
        return response.stateSky?.description ?: "";
    }

    fun getTemperature(response:MunicipioResponse): String{
        return APIHelpers.convertTempToPreferences(response.temperaturaActual?.toLong()!!, applicationContext!!)
    }

    companion object {
        @Volatile
        private var INSTANCE: PronosticoRepository? = null
        fun getInstance(context: Context?): PronosticoRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: PronosticoRepository(context).also {
                    INSTANCE = it
                }
            }
        }
    }
}