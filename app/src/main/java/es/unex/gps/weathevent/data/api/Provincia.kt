package es.unex.gps.weathevent.data.api

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Provincia (

  @SerializedName("CODPROV"                   ) var CODPROV                 : String? = null,
  @SerializedName("NOMBRE_PROVINCIA"          ) var NOMBREPROVINCIA         : String? = null,
  @SerializedName("CODAUTON"                  ) var CODAUTON                : String? = null,
  @SerializedName("COMUNIDAD_CIUDAD_AUTONOMA" ) var COMUNIDADCIUDADAUTONOMA : String? = null,
  @SerializedName("CAPITAL_PROVINCIA"         ) var CAPITALPROVINCIA        : String? = null

) : Serializable