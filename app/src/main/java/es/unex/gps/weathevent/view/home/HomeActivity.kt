package es.unex.gps.weathevent.view.home

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import es.unex.gps.weathevent.R
import es.unex.gps.weathevent.WeathApplication
import es.unex.gps.weathevent.api.APIHelpers
import es.unex.gps.weathevent.api.getElTiempoService
import es.unex.gps.weathevent.data.api.HumedadRelativa
import es.unex.gps.weathevent.data.api.ProximosDias
import es.unex.gps.weathevent.data.api.ProximosDiasArray
import es.unex.gps.weathevent.data.api.ProximosDiasSingle
import es.unex.gps.weathevent.data.api.SensTermica
import es.unex.gps.weathevent.data.api.Temperatura
import es.unex.gps.weathevent.data.api.attributes
import es.unex.gps.weathevent.database.WeathEventDataBase
import es.unex.gps.weathevent.databinding.ActivityMainBinding
import es.unex.gps.weathevent.interfaces.OnCiudadClickListener
import es.unex.gps.weathevent.interfaces.OnClickEventListener
import es.unex.gps.weathevent.interfaces.UserParam
import es.unex.gps.weathevent.model.Ciudad
import es.unex.gps.weathevent.model.Event
import es.unex.gps.weathevent.model.Fecha
import es.unex.gps.weathevent.model.User
import es.unex.gps.weathevent.view.events.EventDetailsActivity
import es.unex.gps.weathevent.view.PerfilActivity
import es.unex.gps.weathevent.view.buscar.BuscarFragmentDirections
import es.unex.gps.weathevent.view.weather.PronosticoActivity
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class HomeActivity : AppCompatActivity(), OnCiudadClickListener {

    private val viewModel: HomeViewModel by viewModels {
        HomeViewModel.provideFactory(application as WeathApplication, this)
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration

    private val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
    }

    companion object {
        const val USER_INFO = "USER_INFO"

        fun start(context: Context, user: User) {
            val intent = Intent(context, HomeActivity::class.java).apply {
                putExtra(USER_INFO, user)
            }

            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra(USER_INFO)) {
            val user = intent.getSerializableExtra(USER_INFO) as User
            viewModel.setUser(user!!)
            setUpUI()
        }
    }

    fun setUpUI() {
        binding.bottomNavigation.setupWithNavController(navController)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.listEventFragment,
                R.id.buscarFragment,
                R.id.favoritosFragment,
                R.id.inicioFragment
            )
        )
        setSupportActionBar(binding.toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_home, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected (item: MenuItem) = when (item.itemId) {
        R.id.action_profile -> {

            val intentPerfilActivity = Intent(this, PerfilActivity::class.java)
            val user = intent.getSerializableExtra(USER_INFO) as? User
            intentPerfilActivity.putExtra("user", user)
            startActivity(intentPerfilActivity)
            true
        }
        R.id.action_settings -> {
            val action = BuscarFragmentDirections.actionGlobalSettingsFragment()
            navController.navigate(action)
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onCiudadClick(ciudad: Ciudad) {
        val intent = Intent(this, PronosticoActivity::class.java)
        intent.putExtra(PronosticoActivity.CIUDAD, ciudad)
        startActivity(intent)
    }
}