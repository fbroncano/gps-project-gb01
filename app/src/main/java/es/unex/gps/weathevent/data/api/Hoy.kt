package es.unex.gps.weathevent.data.api

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Hoy (

  @SerializedName("@attributes"              ) var attributes             : attributes?       = attributes(),
  @SerializedName("estado_cielo"             ) var estadoCielo            : ArrayList<String> = arrayListOf(),
  @SerializedName("precipitacion"            ) var precipitacion          : ArrayList<String> = arrayListOf(),
  @SerializedName("prob_precipitacion"       ) var probPrecipitacion      : ArrayList<String> = arrayListOf(),
  @SerializedName("prob_tormenta"            ) var probTormenta           : ArrayList<String> = arrayListOf(),
  @SerializedName("nieve"                    ) var nieve                  : ArrayList<String> = arrayListOf(),
  @SerializedName("prob_nieve"               ) var probNieve              : ArrayList<String> = arrayListOf(),
  @SerializedName("temperatura"              ) var temperatura            : ArrayList<String> = arrayListOf(),
  @SerializedName("sens_termica"             ) var sensTermica            : ArrayList<String> = arrayListOf(),
  @SerializedName("humedad_relativa"         ) var humedadRelativa        : ArrayList<String> = arrayListOf(),
  @SerializedName("viento"                   ) var viento                 : ArrayList<Viento> = arrayListOf(),
  @SerializedName("racha_max"                ) var rachaMax               : ArrayList<String> = arrayListOf(),
  @SerializedName("estado_cielo_descripcion" ) var estadoCieloDescripcion : ArrayList<String> = arrayListOf()

) : Serializable