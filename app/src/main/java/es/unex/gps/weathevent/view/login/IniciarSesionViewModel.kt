package es.unex.gps.weathevent.view.login

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import es.unex.gps.weathevent.WeathApplication
import es.unex.gps.weathevent.data.repositories.UserRepository
import es.unex.gps.weathevent.model.User
import es.unex.gps.weathevent.util.AppContainer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class IniciarSesionViewModel (
    private val userRepository: UserRepository
): ViewModel() {

    var user: User? = null

    suspend fun login(username: String, password: String): String? {
        if (!username.contains(" ") && !username.replace(" ", "").equals("")) {
            user = userRepository.checkUserSesion(username, password)

            if (user == null) {
                return "El usuario no existe o la contraseña es incorrecta."
            } else {
                return null
            }

        } else {
            return "El usuario no puede contener espacios"
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
                    application.appContainer.userRepository
                ) as T
            }
        }
    }
}