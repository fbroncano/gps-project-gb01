package es.unex.gps.weathevent.data.api

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Comautonoma (

  @SerializedName("ID"       ) var ID       : String? = null,
  @SerializedName("CODAUTON" ) var CODAUTON : String? = null,
  @SerializedName("CODCOMUN" ) var CODCOMUN : String? = null,
  @SerializedName("NOMBRE"   ) var NOMBRE   : String? = null

) : Serializable