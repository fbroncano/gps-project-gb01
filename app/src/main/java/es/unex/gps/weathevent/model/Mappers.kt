package es.unex.gps.weathevent.model

import es.unex.gps.weathevent.data.api.Municipio


fun Municipio.toCiudad() = Ciudad(
    ciudadId = CODIGOINE?.substring(0, 5)?.toLong() ?: 0,
    name = NOMBRE?: "",
    isFavorite = false,
)
