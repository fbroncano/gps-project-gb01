package es.unex.gps.weathevent.data.repositories

import es.unex.gps.weathevent.api.APIError
import es.unex.gps.weathevent.api.ElTiempoAPI
import es.unex.gps.weathevent.data.api.Municipio
import es.unex.gps.weathevent.database.CiudadDao
import es.unex.gps.weathevent.model.Ciudad
import es.unex.gps.weathevent.model.toCiudad

class CiudadesRepository private constructor(
    private val ciudadDao: CiudadDao,
    private val networkService: ElTiempoAPI
) {
    val ciudades = ciudadDao.getCiudades()

    suspend fun tryUpdateRecentCiudadesCache() {
        if (shouldUpdateCiudadesCache())
            fetchCiudades()
    }

    suspend fun getCiudad(ciudadId: Long) : Ciudad {
        return ciudadDao.getCiudad(ciudadId)
    }

    suspend fun getCiudad(ciudadName: String): Ciudad {
        return ciudadDao.getCiudad(ciudadName)
    }

    private suspend fun fetchCiudades() {
        try {
            // Se obtienen todas las provincias de España
            val provinciasResponse = networkService.getProvincias()
            var municipios = arrayListOf<Municipio>()

            // Se obtienen todos los municipios de las provincias de España
            for (provincia in provinciasResponse.provincias) {
                if (provincia.CODPROV != null) {
                    val municipiosResponse = networkService.getMunicipios(provincia.CODPROV!!)
                    municipios.addAll(municipiosResponse.municipios)
                }
            }

            // Se mappean los municipios a ciudad (nombre de municipio e id)
            val ciudades = municipios.map {
                it.toCiudad()
            }

            // Se insertan todos los municipios en el DAO
            ciudadDao.insertAll(ciudades)

        } catch (cause: Throwable) {
            throw APIError("Unable to fetch data from API", cause)
        }
    }

    private suspend fun shouldUpdateCiudadesCache(): Boolean {
        return ciudadDao.getNumberOfCiudades() == 0L
    }

    companion object {
        @Volatile
        private var INSTANCE: CiudadesRepository? = null

        fun getInstance(ciudadDao: CiudadDao, tiempoAPI: ElTiempoAPI): CiudadesRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: CiudadesRepository(ciudadDao, tiempoAPI).also {
                    INSTANCE = it
                }
            }
        }
    }
}