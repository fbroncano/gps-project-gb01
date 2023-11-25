package es.unex.gps.weathevent.api

import android.util.Log
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
}