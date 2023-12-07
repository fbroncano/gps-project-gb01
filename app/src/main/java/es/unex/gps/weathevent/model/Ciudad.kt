package es.unex.gps.weathevent.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Ciudad (
    @PrimaryKey val ciudadId: Long,
    val name: String = "",
): Serializable

data class CiudadFavorite (
    val isFavorite: Long,
    val ciudadId: Long,
    val name: String
)