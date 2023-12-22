package es.unex.gps.weathevent.data.api

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Municipio (

  @SerializedName("CODIGOINE"                ) var CODIGOINE              : String? = null,
  @SerializedName("ID_REL"                   ) var IDREL                  : String? = null,
  @SerializedName("COD_GEO"                  ) var CODGEO                 : String? = null,
  @SerializedName("CODPROV"                  ) var CODPROV                : String? = null,
  @SerializedName("NOMBRE_PROVINCIA"         ) var NOMBREPROVINCIA        : String? = null,
  @SerializedName("NOMBRE"                   ) var NOMBRE                 : String? = null,
  @SerializedName("POBLACION_MUNI"           ) var POBLACIONMUNI          : Int?    = null,
  @SerializedName("SUPERFICIE"               ) var SUPERFICIE             : Double? = null,
  @SerializedName("PERIMETRO"                ) var PERIMETRO              : Int?    = null,
  @SerializedName("CODIGOINE_CAPITAL"        ) var CODIGOINECAPITAL       : String? = null,
  @SerializedName("NOMBRE_CAPITAL"           ) var NOMBRECAPITAL          : String? = null,
  @SerializedName("POBLACION_CAPITAL"        ) var POBLACIONCAPITAL       : String? = null,
  @SerializedName("HOJA_MTN25"               ) var HOJAMTN25              : String? = null,
  @SerializedName("LONGITUD_ETRS89_REGCAN95" ) var LONGITUDETRS89REGCAN95 : Double? = null,
  @SerializedName("LATITUD_ETRS89_REGCAN95"  ) var LATITUDETRS89REGCAN95  : Double? = null,
  @SerializedName("ORIGEN_COORD"             ) var ORIGENCOORD            : String? = null,
  @SerializedName("ALTITUD"                  ) var ALTITUD                : Int?    = null,
  @SerializedName("ORIGEN_ALTITUD"           ) var ORIGENALTITUD          : String? = null,
  @SerializedName("DISCREPANTE_INE"          ) var DISCREPANTEINE         : Int?    = null

) : Serializable