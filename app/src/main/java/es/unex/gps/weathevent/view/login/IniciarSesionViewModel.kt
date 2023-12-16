package es.unex.gps.weathevent.view.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import es.unex.gps.weathevent.WeathApplication
import es.unex.gps.weathevent.data.repositories.UserRepository
import es.unex.gps.weathevent.model.User
import es.unex.gps.weathevent.util.AppContainer

class IniciarSesionViewModel (
    private val userRepository: UserRepository,
    private val appContainer: AppContainer
): ViewModel() {

    var user: User? = null

    fun setUser(): Boolean {
        return if (user != null) {
            appContainer.setUser(user!!)
            true
        } else {
            false
        }
    }

    suspend fun login(username: String, password: String): String? {
        return if (!username.contains(" ") && username.replace(" ", "") != "") {
            user = userRepository.checkUserSesion(username, password)

            if (user == null) {
                "El usuario no existe o la contraseña es incorrecta."
            } else {
                null
            }

        } else {
            "El usuario no puede contener espacios"
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Get the Application object from extras
                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]) as WeathApplication

                return IniciarSesionViewModel(
                    application.appContainer.userRepository,
                    application.appContainer
                ) as T
            }
        }
    }
}