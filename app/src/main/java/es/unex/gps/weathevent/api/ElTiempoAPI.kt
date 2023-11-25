package es.unex.gps.weathevent.api

import es.unex.gps.weathevent.data.api.HomeResponse
import es.unex.gps.weathevent.data.api.MunicipioResponse
import es.unex.gps.weathevent.data.api.MunicipiosResponse
import es.unex.gps.weathevent.data.api.ProvinciaResponse
import es.unex.gps.weathevent.data.api.ProvinciasResponse
import retrofit2.http.GET
import retrofit2.http.Path
interface ElTiempoAPI {
    @GET("home")
    suspend fun getHome() : HomeResponse

    @GET("provincias")
    suspend fun getProvincias() : ProvinciasResponse

    @GET("provincias/{codprov}")
    suspend fun getProvincia(
        @Path("codprov") codprov : String
    ) : ProvinciaResponse

    @GET("provincias/{codprov}/municipios")
    suspend fun getMunicipios(
        @Path("codprov") codprov : String
    ) : MunicipiosResponse

    @GET("provincias/{codprov}/municipios/{codmun}")
    suspend fun getMunicipio(
        @Path("codprov") codprov : String,
        @Path("codmun") codmun : String
    ) : MunicipioResponse
}

class APIError(message: String, cause: Throwable?) : Throwable(message, cause)