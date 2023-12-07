package es.unex.gps.weathevent.view

import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import es.unex.gps.weathevent.R
import es.unex.gps.weathevent.api.APIHelpers
import es.unex.gps.weathevent.api.getElTiempoService
import es.unex.gps.weathevent.data.repositories.FavoritosRepository
import es.unex.gps.weathevent.database.WeathEventDataBase
import es.unex.gps.weathevent.databinding.ActivityPronosticoBinding
import es.unex.gps.weathevent.interfaces.CiudadParam
import es.unex.gps.weathevent.model.Ciudad
import es.unex.gps.weathevent.model.User
import kotlinx.coroutines.launch

class PronosticoActivity : AppCompatActivity(), CiudadParam {

    private lateinit var binding : ActivityPronosticoBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private var ciudad : Ciudad? = null
    private lateinit var user : User
    private lateinit var favoritosRepository: FavoritosRepository

    private val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.pronostico_nav_host_fragment) as NavHostFragment).navController
    }

    private lateinit var db: WeathEventDataBase

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPronosticoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ciudad = intent.getSerializableExtra(CIUDAD) as Ciudad?
        user = intent.getSerializableExtra(USER_INFO) as User
        db = WeathEventDataBase.getInstance(this)!!

        favoritosRepository = FavoritosRepository.getInstance(db.favoritoDao())
        binding.pronosticoNavigation.setupWithNavController(navController)
        setUiViews()
        ciudadBinding(ciudad!!)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_home, menu)
        return super.onCreateOptionsMenu(menu)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setUiViews() {
        lifecycleScope.launch {
            if (ciudad?.ciudadId != null) {
                val codProv = APIHelpers.getCodProv(ciudad?.ciudadId!!)
                val ciudadId = APIHelpers.getCiudadFormat(ciudad?.ciudadId!!)

                val response = getElTiempoService().getMunicipio(codProv, ciudadId)

                binding.municipioView.text = ciudad?.name
                binding.descripcionView.text = response.stateSky?.description
                binding.temperatureView.text = APIHelpers.convertTempToPreferences(response.temperaturaActual?.toLong()!!, applicationContext)
            } else {
                binding.municipioView.text = "No hay municipio a mostrar"
            }
        }
    }

    private fun ciudadBinding(ciudad: Ciudad) {

        lifecycleScope.launch {
            val isFavorite = favoritosRepository.checkFavorite(ciudad.ciudadId)

            if (isFavorite) {
                binding.imageFav.setImageResource(R.drawable.baseline_favorite_border_40)
            } else {
                binding.imageFav.setImageResource(R.drawable.baseline_favorite_40_red)
            }

            binding.imageFav.setOnClickListener {
                lifecycleScope.launch {
                    val isFavorite = favoritosRepository.checkFavorite(ciudad.ciudadId)

                    if (isFavorite) {
                        favoritosRepository.markFavorite(ciudad.ciudadId)
                        binding.imageFav.setImageResource(R.drawable.baseline_favorite_40_red)
                        Toast.makeText(
                            this@PronosticoActivity,
                            "${ciudad.name} añadido a favoritos",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        favoritosRepository.desmarkFavorite(ciudad.ciudadId)
                        binding.imageFav.setImageResource(R.drawable.baseline_favorite_border_40)
                        Toast.makeText(
                            this@PronosticoActivity,
                            "${ciudad.name} eliminado de favoritos",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    override fun getCiudadParam() : Ciudad? {
        return ciudad
    }

    companion object {
        val CIUDAD = "Ciudad"
        val USER_INFO = "User"
    }
}