package es.unex.gps.weathevent.model

import java.io.Serializable

data class TiempoPorHora (
    var hora : String,
    var temperatura : String,
    var estadoCieloDescripcion : String
): Serializable