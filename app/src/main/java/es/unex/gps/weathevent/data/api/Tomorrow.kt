package es.unex.gps.weathevent.data.api

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Tomorrow (

  @SerializedName("p" ) var p : ArrayList<String> = arrayListOf()

) : Serializable