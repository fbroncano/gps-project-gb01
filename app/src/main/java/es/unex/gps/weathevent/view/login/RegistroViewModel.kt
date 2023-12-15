package es.unex.gps.weathevent.view.login

import android.os.Bundle
import android.util.Patterns
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.savedstate.SavedStateRegistryOwner
import es.unex.gps.weathevent.WeathApplication
import es.unex.gps.weathevent.data.repositories.UserRepository
import es.unex.gps.weathevent.model.User

class RegistroViewModel(
    val userRepository: UserRepository
): ViewModel() {

    fun validateEmail(email: String): String? {
        return if (email.isEmpty()) {
            "El correo electrónico no puede estar vacío."
        } else if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            "El correo electrónico no es válido."
        } else {
            null
        }
    }

    fun validateName(name: String): String? {
        return if (name.isEmpty()) {
            "El nombre no puede estar vacío."
        } else {
            null
        }
    }

    fun validateUsername(username: String): String? {
        return if (username.isEmpty()) {
            "El usuario no puede estar vacio."
        } else if (username.contains(" ")) {
            "No puede contener espacios en blanco."
        } else {
            null
        }
    }

    fun validatePassword(password: String): String? {
        return if (password.isEmpty()) {
            "La contraseña no puede estar vacia."
        } else if (password.contains(" ")) {
            "No puede contener espacios en blanco."
        } else {
            null
        }
    }

    suspend fun register(user: User): String? {
        val other = userRepository.checkUserSesion(user.username, user.password)

        return if (other == null) {
            user.userId = userRepository.insertUser(user)
            null
        } else {
            "El nombre de usuario ya existe"
        }
    }

    companion object {
        fun provideFactory(
            userRepository: UserRepository,
            owner: SavedStateRegistryOwner,
            defaultArgs: Bundle? = null,
        ): AbstractSavedStateViewModelFactory =
            object : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(
                    key: String,
                    modelClass: Class<T>,
                    handle: SavedStateHandle
                ): T {
                    return RegistroViewModel(userRepository) as T
                }
            }
    }
}