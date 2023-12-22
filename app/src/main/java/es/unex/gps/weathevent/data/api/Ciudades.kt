package es.unex.gps.weathevent.data.api

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Ciudades (

  @SerializedName("id"           ) var id           : String?       = null,
  @SerializedName("idProvince"   ) var idProvince   : String?       = null,
  @SerializedName("name"         ) var name         : String?       = null,
  @SerializedName("nameProvince" ) var nameProvince : String?       = null,
  @SerializedName("stateSky"     ) var stateSky     : StateSky?     = StateSky(),
  @SerializedName("temperatures" ) var temperaturas : Temperaturas? = Temperaturas()

) : Serializable