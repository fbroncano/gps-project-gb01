package es.unex.gps.weathevent.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.io.Serializable

@Entity
data class Event(
    @PrimaryKey(autoGenerate = true) val id: Long?,
    val name : String,
    val location : String,
    @Embedded val date : Fecha,
    val userid : Long,
    val locationId : Long
) : Serializable

data class UserWithEvent(
    @Embedded val user_val : User,
    @Relation(
        parentColumn = "id",
        entityColumn = "user_val"
    )
    val events : Array<Event>
)