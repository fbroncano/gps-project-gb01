package es.unex.gps.weathevent.model



import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/*
 * Modelo de clase usuario
 */
@Entity
data class User(
    @PrimaryKey(autoGenerate = true) var userId: Long?,
    val name: String = "",
    val username: String = "",
    val email: String = "",
    val password: String = ""
) : Serializable