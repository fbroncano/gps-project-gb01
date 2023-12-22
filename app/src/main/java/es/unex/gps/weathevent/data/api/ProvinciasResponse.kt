package es.unex.gps.weathevent.data.api

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class ProvinciasResponse (

  @SerializedName("origen"          ) var origen          : Origen?               = Origen(),
  @SerializedName("title"           ) var title           : String?               = null,
  @SerializedName("provincias"      ) var provincias      : ArrayList<Provincia>  = arrayListOf(),
  @SerializedName("metadescripcion" ) var metadescripcion : String?               = null,
  @SerializedName("keywords"        ) var keywords        : String?               = null,
  @SerializedName("breadcrumb"      ) var breadcrumb      : ArrayList<Breadcrumb> = arrayListOf()

) : Serializable