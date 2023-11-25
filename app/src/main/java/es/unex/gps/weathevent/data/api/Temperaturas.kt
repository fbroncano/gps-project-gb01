package es.unex.gps.weathevent.data.api

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Temperaturas (

  @SerializedName("max" ) var max : String? = null,
  @SerializedName("min" ) var min : String? = null

) : Serializable