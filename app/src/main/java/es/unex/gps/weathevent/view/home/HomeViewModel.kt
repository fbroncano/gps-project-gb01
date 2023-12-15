package es.unex.gps.weathevent.view.home

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import es.unex.gps.weathevent.WeathApplication
import es.unex.gps.weathevent.data.repositories.UserRepository
import es.unex.gps.weathevent.model.User
import es.unex.gps.weathevent.util.AppContainer
import es.unex.gps.weathevent.view.login.RegistroViewModel

class HomeViewModel(
    private val appContainer: AppContainer
): ViewModel() {

    fun setUser(user: User) {
        appContainer.setUser(user)
    }

    companion object {
        fun provideFactory(
            application: WeathApplication,
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
                    return HomeViewModel(application.appContainer) as T
                }
            }
    }
}