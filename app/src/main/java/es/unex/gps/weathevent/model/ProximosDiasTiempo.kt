package es.unex.gps.weathevent.model

import java.io.Serializable
data class ProximosDiasTiempo (
    var dia : String,
    var temperatura : String,
    var estadoCieloDescripcion : String,
    var sensTermica : String,
    var precipitacion : String,
    var viento : String
): Serializable