package es.unex.gps.weathevent.data.api

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Pronostico (

  @SerializedName("hoy"    ) var hoy    : Hoy?    = Hoy(),
  @SerializedName("manana" ) var manana : Manana? = Manana()

) : Serializable