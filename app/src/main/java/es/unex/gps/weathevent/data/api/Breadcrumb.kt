package es.unex.gps.weathevent.data.api

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Breadcrumb (

  @SerializedName("name"  ) var name  : String? = null,
  @SerializedName("url"   ) var url   : String? = null,
  @SerializedName("title" ) var title : String? = null

) : Serializable