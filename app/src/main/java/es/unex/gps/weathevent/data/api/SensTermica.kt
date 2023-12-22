package es.unex.gps.weathevent.data.api

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class SensTermica (

  @SerializedName("maxima" ) var maxima : String? = null,
  @SerializedName("minima" ) var minima : String? = null

) : Serializable