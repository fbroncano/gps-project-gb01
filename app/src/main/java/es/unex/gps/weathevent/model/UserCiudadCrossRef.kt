package es.unex.gps.weathevent.model

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    primaryKeys = ["userId", "ciudadId"],
    foreignKeys = [
        ForeignKey(
            entity = Ciudad::class,
            parentColumns = ["ciudadId"],
            childColumns = ["ciudadId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class UserCiudadCrossRef(
    val userId: Long,
    val ciudadId: Long
)