package es.unex.gps.weathevent.view.weather

import android.os.Build
import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupWithNavController
import es.unex.gps.weathevent.R
import es.unex.gps.weathevent.database.WeathEventDataBase
import es.unex.gps.weathevent.databinding.ActivityPronosticoBinding
import es.unex.gps.weathevent.model.Ciudad
import es.unex.gps.weathevent.view.PronosticoViewModel

class PronosticoActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPronosticoBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    private val viewModel: PronosticoViewModel by viewModels{PronosticoViewModel.Factory}

    private val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.pronostico_nav_host_fragment) as NavHostFragment).navController
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPronosticoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.binding = binding
        viewModel._ciudad.value = intent.getSerializableExtra(CIUDAD) as Ciudad?

        binding.pronosticoNavigation.setupWithNavController(navController)
        viewModel.setUiViews()
        viewModel.ciudadBinding(this)
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
    }
}