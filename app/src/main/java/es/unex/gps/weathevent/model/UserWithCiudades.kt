package es.unex.gps.weathevent.model;

import androidx.room.Embedded;
import androidx.room.Relation;
import androidx.room.Junction;
data class UserWithCiudades(
    @Embedded val user: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "ciudadId",
        associateBy = Junction(UserCiudadCrossRef::class)
    )
    val ciudades: List<Ciudad>
)