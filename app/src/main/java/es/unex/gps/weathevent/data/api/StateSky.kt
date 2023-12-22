package es.unex.gps.weathevent.data.api

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class StateSky (

  @SerializedName("description" ) var description : String? = null,
  @SerializedName("id"          ) var id          : String? = null

) : Serializable