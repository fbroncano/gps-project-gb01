package es.unex.gps.weathevent.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Ciudad (
    @PrimaryKey(autoGenerate = true) val ciudadId: Long,
    val name: String = "",
    @ColumnInfo(name = "is_favorite") var isFavorite: Boolean,
): Serializable