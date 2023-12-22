package es.unex.gps.weathevent.data.api

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Viento (

  @SerializedName("@attributes" ) var attributes  : attributes? = attributes(),
  @SerializedName("direccion"   ) var direccion   : String?      = null,
  @SerializedName("velocidad"   ) var velocidad   : String?      = null

) : Serializable