package es.unex.gps.weathevent.data.api

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class attributes (

  @SerializedName("periodo" ) var periodo : String? = null,
  @SerializedName("fecha" )   var fecha : String? = null,
  @SerializedName("orto" )    var orto : String? = null,
  @SerializedName("ocaso" )   var ocaso : String? = null

) : Serializable