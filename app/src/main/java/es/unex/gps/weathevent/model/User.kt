package es.unex.gps.weathevent.Model



import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

/*
 * Modelo de clase usuario
 */
@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val userId: Long?,
    val name: String = "",
    val username: String = "",
    val email: String = "",
    val password: String = ""
) : Serializable {

    fun validate(): Boolean {
        var valido = true

        valido = validateName() && valido
        valido = validateUsername() && valido
        valido = validateEmail() && valido
        valido = validatePassword() && valido

        return valido
    }

    fun validateName(): Boolean {
        return name.isNotEmpty()
    }

    fun validateUsername(): Boolean {
        return username.isNotEmpty() && !username.contains(" ")
    }

    fun validateEmail(): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun validatePassword(): Boolean {
        return password.isNotEmpty() && !password.contains(" ")
    }
}