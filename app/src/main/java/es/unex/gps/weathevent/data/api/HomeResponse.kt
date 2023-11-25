package es.unex.gps.weathevent.data.api

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class HomeResponse (

  @SerializedName("origen"          ) var origen          : Origen?               = Origen(),
  @SerializedName("title"           ) var title           : String?               = null,
  @SerializedName("ciudades"        ) var ciudades        : ArrayList<Ciudades>   = arrayListOf(),
  @SerializedName("today"           ) var today           : Today?                = Today(),
  @SerializedName("tomorrow"        ) var tomorrow        : Tomorrow?             = Tomorrow(),
  @SerializedName("provincias"      ) var provincia       : ArrayList<Provincia>  = arrayListOf(),
  @SerializedName("breadcrumb"      ) var breadcrumb      : ArrayList<String>     = arrayListOf(),
  @SerializedName("metadescripcion" ) var metadescripcion : String?               = null,
  @SerializedName("keywords"        ) var keywords        : String?               = null

) : Serializable