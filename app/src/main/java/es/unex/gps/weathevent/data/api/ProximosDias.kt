package es.unex.gps.weathevent.data.api

import com.google.gson.annotations.SerializedName
import java.io.Serializable

interface ProximosDias : Serializable {}

// Los dos primeros ProximosDias (0, 1) son de esta forma
data class ProximosDiasArray (

  @SerializedName("@attributes"              ) var attributes             : attributes?              = attributes(),
  @SerializedName("prob_precipitacion"       ) var probPrecipitacion      : ArrayList<String>        = arrayListOf(),
  // @SerializedName("cota_nieve_prov"          ) var cotaNieveProv          : ArrayList<CotaNieveProv> = arrayListOf(),
  @SerializedName("estado_cielo"             ) var estadoCielo            : ArrayList<String>        = arrayListOf(),
  @SerializedName("viento"                   ) var viento                 : ArrayList<Viento>        = arrayListOf(),
  // @SerializedName("racha_max"                ) var rachaMax               : ArrayList<RachaMax>      = arrayListOf(),
  @SerializedName("temperatura"              ) var temperatura            : Temperatura?             = Temperatura(),
  @SerializedName("sens_termica"             ) var sensTermica            : SensTermica?             = SensTermica(),
  @SerializedName("humedad_relativa"         ) var humedadRelativa        : HumedadRelativa?         = HumedadRelativa(),
  @SerializedName("uv_max"                   ) var uvMax                  : String?                  = null,
  @SerializedName("estado_cielo_descripcion" ) var estadoCieloDescripcion : ArrayList<String>        = arrayListOf()

) : ProximosDias, Serializable

// Los tres últimos ProximosDias (2, 3, 4) son de esta forma
data class ProximosDiasSingle (

  @SerializedName("@attributes"              ) var attributes             : attributes?              = attributes(),
  @SerializedName("prob_precipitacion"       ) var probPrecipitacion      : String?                  = null,
  // @SerializedName("cota_nieve_prov"          ) var cotaNieveProv          : CotaNieveProv?           = CotaNieveProv(),
  @SerializedName("estado_cielo"             ) var estadoCielo            : String?                  = null,
  @SerializedName("viento"                   ) var viento                 : Viento?                  = Viento(),
  // @SerializedName("racha_max"                ) var rachaMax               : RachaMax?                = RachaMax(),
  @SerializedName("temperatura"              ) var temperatura            : Temperatura?             = Temperatura(),
  @SerializedName("sens_termica"             ) var sensTermica            : SensTermica?             = SensTermica(),
  @SerializedName("humedad_relativa"         ) var humedadRelativa        : HumedadRelativa?         = HumedadRelativa(),
  @SerializedName("uv_max"                   ) var uvMax                  : String?                  = null,
  @SerializedName("estado_cielo_descripcion" ) var estadoCieloDescripcion : String?                  = null

) : ProximosDias, Serializable