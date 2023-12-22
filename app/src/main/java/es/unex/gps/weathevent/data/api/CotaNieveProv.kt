package es.unex.gps.weathevent.data.api

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class CotaNieveProv (

  @SerializedName("@attributes" ) var attributes : attributes? = attributes()

) : Serializable