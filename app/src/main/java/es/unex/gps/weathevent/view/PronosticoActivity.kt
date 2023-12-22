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
<<<<<<< HEAD:app/src/main/java/es/unex/gps/weathevent/PronosticoActivity.kt
import es.unex.gps.weathevent.Model.Ciudad
import es.unex.gps.weathevent.Model.User
=======
import es.unex.gps.weathevent.R
>>>>>>> origin/develop:app/src/main/java/es/unex/gps/weathevent/view/PronosticoActivity.kt
import es.unex.gps.weathevent.api.APIHelpers
import es.unex.gps.weathevent.api.getElTiempoService
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

        if (!ciudad.isFavorite){
            binding.imageFav.setImageResource(R.drawable.baseline_favorite_border_40)
        }else{
            binding.imageFav.setImageResource(R.drawable.baseline_favorite_40_red)
        }

        binding.imageFav.setOnClickListener {
            lifecycleScope.launch {
                if (!ciudad.isFavorite) {
                    ciudad.isFavorite = true
                    user.userId?.let { it1 -> db.ciudadDao().insertAndRelate(ciudad, it1) }
                    binding.imageFav.setImageResource(R.drawable.baseline_favorite_40_red)
                    Toast.makeText(this@PronosticoActivity, "${ciudad.name} añadido a favoritos", Toast.LENGTH_SHORT).show()
                } else {
                    ciudad.isFavorite = false
                    db.ciudadDao().delete(ciudad)
                    binding.imageFav.setImageResource(R.drawable.baseline_favorite_border_40)
                    Toast.makeText(this@PronosticoActivity, "${ciudad.name} eliminado de favoritos", Toast.LENGTH_SHORT).show()
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