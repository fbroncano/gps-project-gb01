package es.unex.gps.weathevent.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import es.unex.gps.weathevent.R
import es.unex.gps.weathevent.WeathApplication
import es.unex.gps.weathevent.api.APIHelpers
import es.unex.gps.weathevent.api.getElTiempoService
import es.unex.gps.weathevent.data.repositories.FavoritosRepository
import es.unex.gps.weathevent.databinding.ActivityPronosticoBinding
import es.unex.gps.weathevent.model.Ciudad
import es.unex.gps.weathevent.model.User
import kotlinx.coroutines.launch

class PronosticoViewModel(
    private val favoritosRepository: FavoritosRepository
): ViewModel()  {
    var applicationContext: Context? = null
    var user: User? = null

    val _ciudad = MutableLiveData<Ciudad>(null)
    var ciudad: LiveData<Ciudad> = TODO()
        get() = _ciudad
    var ciudadActual: Ciudad? = null
        set(value) {
            field = value
            _ciudad.value = value!!
        }
    lateinit var binding : ActivityPronosticoBinding

    fun init(context : Context){
        applicationContext = context
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun setUiViews() {
        viewModelScope.launch {
            if (ciudad.value?.ciudadId != null) {
                val codProv = APIHelpers.getCodProv(ciudad.value!!.ciudadId)
                val ciudadId = APIHelpers.getCiudadFormat(ciudad.value!!.ciudadId)

                val response = getElTiempoService().getMunicipio(codProv, ciudadId)

                binding.municipioView.text = ciudad.value!!.name
                binding.descripcionView.text = response.stateSky?.description
                binding.temperatureView.text = APIHelpers.convertTempToPreferences(response.temperaturaActual?.toLong()!!, applicationContext!!)
            } else {
                binding.municipioView.text = "No hay municipio a mostrar"
            }
        }
    }

    fun ciudadBinding() {

        viewModelScope.launch {
            val isFavorite = favoritosRepository.checkFavorite(user?.userId!!, ciudad.value!!.ciudadId)

            if (isFavorite) {
                binding.imageFav.setImageResource(R.drawable.baseline_favorite_border_40)
            } else {
                binding.imageFav.setImageResource(R.drawable.baseline_favorite_40_red)
            }

            binding.imageFav.setOnClickListener {
                viewModelScope.launch {
                    val isFavorite = favoritosRepository.checkFavorite(user?.userId!!, ciudad.value!!.ciudadId)

                    if (isFavorite) {
                        favoritosRepository.markFavorite(user?.userId!!, ciudad.value!!.ciudadId)
                        binding.imageFav.setImageResource(R.drawable.baseline_favorite_40_red)
                        /*Toast.makeText(
                            this,
                            "${ciudad?.name} añadido a favoritos",
                            Toast.LENGTH_SHORT
                        ).show()*/
                    } else {
                        favoritosRepository.desmarkFavorite(user?.userId!!, ciudad.value!!.ciudadId)
                        binding.imageFav.setImageResource(R.drawable.baseline_favorite_border_40)
                        /*
                        Toast.makeText(
                            this@PronosticoViewModel!!,
                            "${ciudad?.name} eliminado de favoritos",
                            Toast.LENGTH_SHORT
                        ).show()*/
                    }
                }
            }
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

                return PronosticoViewModel(
                    (application as WeathApplication).appContainer.favoritosRepository
                ) as T
            }
        }
    }
}