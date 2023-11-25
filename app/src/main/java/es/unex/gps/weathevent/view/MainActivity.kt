package es.unex.gps.weathevent.view

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import es.unex.gps.weathevent.R
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
import es.unex.gps.weathevent.interfaces.UserParam
import es.unex.gps.weathevent.model.Ciudad
import es.unex.gps.weathevent.model.Fecha
import es.unex.gps.weathevent.model.User
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class MainActivity : AppCompatActivity(), UserParam, OnCiudadClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var db : WeathEventDataBase

    private val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
    }

    var user: User? = null
    companion object {
        const val USER_INFO = "USER_INFO"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = WeathEventDataBase.getInstance(this)

        if (intent.hasExtra(USER_INFO)) {
            user = intent.getSerializableExtra(USER_INFO) as User
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

    override fun getUserFragment(): User {
        return user!!
    }

    override fun onCiudadClick(ciudad: Ciudad) {
        val intent = Intent(this, PronosticoActivity::class.java)
        intent.putExtra(PronosticoActivity.CIUDAD, ciudad)
        intent.putExtra(PronosticoActivity.USER_INFO, user)
        startActivity(intent)
    }
}