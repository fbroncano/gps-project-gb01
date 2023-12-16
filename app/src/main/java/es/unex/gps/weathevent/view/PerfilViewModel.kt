package es.unex.gps.weathevent.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import es.unex.gps.weathevent.WeathApplication
import es.unex.gps.weathevent.data.repositories.UserRepository
import es.unex.gps.weathevent.model.User
import kotlinx.coroutines.launch

class PerfilViewModel(
    private val userRepository: UserRepository
): ViewModel() {

    val user = userRepository.user

    fun updateUser(newUser: User) {
        user.value = newUser

        viewModelScope.launch {
            userRepository.updateUser(newUser)
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
                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])

                return PerfilViewModel(
                    (application as WeathApplication).appContainer.userRepository
                ) as T
            }
        }
    }
}