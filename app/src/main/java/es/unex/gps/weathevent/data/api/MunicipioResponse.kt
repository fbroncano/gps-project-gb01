package es.unex.gps.weathevent.data.api

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class MunicipioResponse (

  @SerializedName("origin"             ) var origen            : Origen?                 = Origen(),
  @SerializedName("title"              ) var title             : String?                 = null,
  @SerializedName("metadescripcion"    ) var metadescripcion   : String?                 = null,
  @SerializedName("keywords"           ) var keywords          : String?                 = null,
  @SerializedName("municipio"          ) var municipio         : Municipio?              = Municipio(),
  @SerializedName("fecha"              ) var fecha             : String?                 = null,
  @SerializedName("stateSky"           ) var stateSky          : StateSky?               = StateSky(),
  @SerializedName("temperatura_actual" ) var temperaturaActual : String?                 = null,
  @SerializedName("temperaturas"       ) var temperaturas      : Temperaturas?           = Temperaturas(),
  @SerializedName("humedad"            ) var humedad           : String?                 = null,
  @SerializedName("viento"             ) var viento            : String?                 = null,
  @SerializedName("precipitacion"      ) var precipitacion     : String?                 = null,
  @SerializedName("lluvia"             ) var lluvia            : String?                 = null,
  @SerializedName("imagen"             ) var imagen            : String?                 = null,
  @SerializedName("pronostico"         ) var pronostico        : Pronostico?             = Pronostico(),
  @SerializedName("proximos_dias"      ) var proximosDiasJson  : JsonArray?              = null,
  @SerializedName("breadcrumb"         ) var breadcrumb        : ArrayList<Breadcrumb>   = arrayListOf(),
  var proximosDias : ArrayList<ProximosDias> = arrayListOf()

) : Serializable {
  fun obtainProximosDias() {
    if (proximosDiasJson?.size() == 5) {
      proximosDias.add(Gson().fromJson(proximosDiasJson?.get(0), ProximosDiasArray::class.java))
      proximosDias.add(Gson().fromJson(proximosDiasJson?.get(1), ProximosDiasArray::class.java))
      proximosDias.add(Gson().fromJson(proximosDiasJson?.get(2), ProximosDiasSingle::class.java))
      proximosDias.add(Gson().fromJson(proximosDiasJson?.get(3), ProximosDiasSingle::class.java))
      proximosDias.add(Gson().fromJson(proximosDiasJson?.get(4), ProximosDiasSingle::class.java))
    } else if (proximosDiasJson?.size() == 6) {
      proximosDias.add(Gson().fromJson(proximosDiasJson?.get(0), ProximosDiasArray::class.java))
      proximosDias.add(Gson().fromJson(proximosDiasJson?.get(1), ProximosDiasArray::class.java))
      proximosDias.add(Gson().fromJson(proximosDiasJson?.get(2), ProximosDiasArray::class.java))
      proximosDias.add(Gson().fromJson(proximosDiasJson?.get(3), ProximosDiasSingle::class.java))
      proximosDias.add(Gson().fromJson(proximosDiasJson?.get(4), ProximosDiasSingle::class.java))
      proximosDias.add(Gson().fromJson(proximosDiasJson?.get(5), ProximosDiasSingle::class.java))
    }

  }
}