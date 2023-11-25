package es.unex.gps.weathevent.data.api

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class ProvinciaResponse (

  @SerializedName("origen"          ) var origen          : Origen?               = Origen(),
  @SerializedName("title"           ) var title           : String?               = null,
  @SerializedName("today"           ) var today           : TodayString?          = TodayString(),
  @SerializedName("tomorrow"        ) var tomorrow        : TomorrowString        = TomorrowString(),
  @SerializedName("ciudades"        ) var ciudades        : ArrayList<Ciudades>   = arrayListOf(),
  @SerializedName("provincia"       ) var provincia       : Provincia?            = Provincia(),
  @SerializedName("comautonoma"     ) var comautonoma     : Comautonoma?          = Comautonoma(),
  @SerializedName("metadescripcion" ) var metadescripcion : String?               = null,
  @SerializedName("keywords"        ) var keywords        : String?               = null,
  @SerializedName("breadcrumb"      ) var breadcrumb      : ArrayList<Breadcrumb> = arrayListOf()

) : Serializable