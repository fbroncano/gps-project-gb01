package es.unex.gps.weathevent.view

import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
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
import es.unex.gps.weathevent.view.home.HomeViewModel
import kotlinx.coroutines.launch

class PronosticoActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPronosticoBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var favoritosRepository: FavoritosRepository

    private val homeViewModel: HomeViewModel by viewModels()
    private val viewModel: PronosticoViewModel by viewModels{ PronosticoViewModel.Factory}

    private val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.pronostico_nav_host_fragment) as NavHostFragment).navController
    }

    private lateinit var db: WeathEventDataBase

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPronosticoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.init(applicationContext)
        homeViewModel.user.observe(this) { user ->
            viewModel.user = user
        }

        viewModel.binding = binding

        viewModel.ciudadActual = intent.getSerializableExtra(CIUDAD) as Ciudad?
        db = WeathEventDataBase.getInstance(this)!!

        favoritosRepository = FavoritosRepository.getInstance(db.favoritoDao())
        binding.pronosticoNavigation.setupWithNavController(navController)
        viewModel.setUiViews()
        viewModel.ciudadBinding()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_home, menu)
        return super.onCreateOptionsMenu(menu)
    }
    companion object {
        val CIUDAD = "Ciudad"
        val USER_INFO = "User"
    }
}