package es.unex.gps.weathevent.data.api

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class MunicipiosResponse (

  @SerializedName("origen"          ) var origen          : Origen?               = Origen(),
  @SerializedName("title"           ) var title           : String?               = null,
  @SerializedName("provincia"       ) var provincia       : String?               = null,
  @SerializedName("codprov"         ) var codprov         : String?               = null,
  @SerializedName("metadescripcion" ) var metadescripcion : String?               = null,
  @SerializedName("keywords"        ) var keywords        : String?               = null,
  @SerializedName("municipios"      ) var municipios      : ArrayList<Municipio>  = arrayListOf(),
  @SerializedName("breadcrumb"      ) var breadcrumb      : ArrayList<Breadcrumb> = arrayListOf()

) : Serializable