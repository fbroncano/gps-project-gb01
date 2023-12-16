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
    var userid : Long,
    val locationId : Long
) : Serializable
