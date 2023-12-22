package es.unex.gps.weathevent.data.api

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Origen (

  @SerializedName("productor"   ) var productor   : String? = null,
  @SerializedName("web"         ) var web         : String? = null,
  @SerializedName("language"    ) var language    : String? = null,
  @SerializedName("copyright"   ) var copyright   : String? = null,
  @SerializedName("nota_legal"  ) var notaLegal   : String? = null,
  @SerializedName("descripcion" ) var descripcion : String? = null

) : Serializable