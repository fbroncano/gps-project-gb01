package es.unex.gps.weathevent.model

import androidx.room.ColumnInfo
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
        ),

        ForeignKey(
            entity = User::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
)
data class Favorito (
    @ColumnInfo(index = true)
    val userId: Long,

    @ColumnInfo(index = true)
    val ciudadId: Long
)