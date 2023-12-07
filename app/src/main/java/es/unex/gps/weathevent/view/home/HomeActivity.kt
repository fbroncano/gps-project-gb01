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
import androidx.fragment.app.activityViewModels
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
import es.unex.gps.weathevent.interfaces.OnClickEventListener
import es.unex.gps.weathevent.interfaces.UserParam
import es.unex.gps.weathevent.model.Ciudad
import es.unex.gps.weathevent.model.Event
import es.unex.gps.weathevent.model.Fecha
import es.unex.gps.weathevent.model.User
import es.unex.gps.weathevent.view.EventDetailsActivity
import es.unex.gps.weathevent.view.login.IniciarSesionViewModel
import es.unex.gps.weathevent.view.PerfilActivity
import es.unex.gps.weathevent.view.PronosticoActivity
import kotlinx.coroutines.launch
import java.time.LocalDateTime

class HomeActivity : AppCompatActivity(), UserParam, OnCiudadClickListener, OnClickEventListener {

    private val viewModel: IniciarSesionViewModel by viewModels { IniciarSesionViewModel.Factory}
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var db : WeathEventDataBase

    private val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
    }

    var user: User? = null
    companion object {
        const val USER_INFO = "USER_INFO"

        fun start(
            context: Context,
            user: User,
        ) {
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

        db = WeathEventDataBase.getInstance(this)

        homeViewModel.userInSession = intent.getSerializableExtra(USER_INFO) as User

        if (intent.hasExtra(USER_INFO)) {
            user = intent.getSerializableExtra(USER_INFO) as User
            // viewModel.setUser(user!!)
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

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onEventClick(event: Event) {
        var proximoDia : ProximosDias? = null
        var pronostico : Boolean

        val intent = Intent(this, EventDetailsActivity::class.java)
        intent.putExtra(EventDetailsActivity.EVENT, event)
        lifecycleScope.launch {
            // Si la fecha del evento es el actual dia o el siguiente, se manda el Pronostico
            val time = LocalDateTime.now()
            val date = Fecha(time.dayOfMonth, time.monthValue, time.year, time.hour, time.minute).getAbsoluteDay()

            Log.d("DIASEVENTO", date.toString() + " " + event.date.getAbsoluteDay().toString())
            if (date == event.date.getAbsoluteDay() || date + 1 == event.date.getAbsoluteDay()){
                // Comprobar si hay tiempo para esa hora
                val municipioResponse = getElTiempoService().getMunicipio(APIHelpers.getCodProv(event.locationId), APIHelpers.getCiudadFormat(event.locationId))
                val horasManana = municipioResponse.pronostico?.manana?.viento?.map {
                    it.attributes?.periodo
                }
                val strHora : String? = if (event.date.hora < 10) "0${event.date.hora}" else event.date.hora.toString()

                if (horasManana?.indexOf(strHora) != -1) {
                    proximoDia = sendPronosticoData(event)
                    pronostico = true
                } else {
                    proximoDia = sendWeatherData(event)
                    pronostico = false
                    Log.d("APIResult", proximoDia.toString())
                }
            } else {
                // Si no se tienen los datos de pronóstico, se manda predicción
                proximoDia = sendWeatherData(event)
                pronostico = false
                Log.d("APIResult", proximoDia.toString())
            }

            intent.putExtra(EventDetailsActivity.PREDICCION, proximoDia)
            intent.putExtra(EventDetailsActivity.PRONOSTICO, pronostico)
            startActivity(intent)
        }
    }

    override fun onEventDelete(event: Event) {
        lifecycleScope.launch {
            db.eventDao().deleteEvent(event)
        }

        Toast.makeText(this, "Borrado el elemento", Toast.LENGTH_SHORT).show()
    }

    override fun getUserFragment(): User {
        return user!!
    }

    suspend fun sendWeatherData(event: Event): ProximosDias? {
        var proximoDia: ProximosDias? = null
        var locationId = event.locationId.toString()

        if (locationId.length == 4) locationId = "0$locationId"

        val codprov = locationId.subSequence(0, locationId.length - 3).toString()
        val municipioResponse = getElTiempoService().getMunicipio(codprov, locationId)
        municipioResponse.obtainProximosDias()

        // Comprobar el objeto ProximoDia
        for (obj in municipioResponse.proximosDias) {
            if (obj is ProximosDiasArray) {
                val dates = obj.attributes?.fecha?.split("-")
                val fecha = Fecha(dates?.get(2)?.toInt()!!, dates?.get(1)?.toInt()!!, dates?.get(0)?.toInt()!!, 0, 0)

                Log.d("DatesCast", "" + fecha.getAbsoluteDay() + "  " + event.date.getAbsoluteDay())
                if (fecha.getAbsoluteDay() == event.date.getAbsoluteDay()) {
                    proximoDia = obj
                }
            } else if (obj is ProximosDiasSingle) {
                val dates = obj.attributes?.fecha?.split("-")
                val fecha = Fecha(dates?.get(2)?.toInt()!!, dates?.get(1)?.toInt()!!, dates?.get(0)?.toInt()!!, 0, 0)

                if (fecha.getAbsoluteDay() == event.date.getAbsoluteDay()) {
                    proximoDia = obj
                }
            }
        }

        return proximoDia
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun sendPronosticoData(event: Event) : ProximosDias? {
        var proximo: ProximosDias? = null
        var locationId = event.locationId.toString()
        var time = LocalDateTime.now()
        val date = Fecha(time.dayOfMonth, time.monthValue, time.year, time.hour, time.minute).getAbsoluteDay()

        if (locationId.length == 4) locationId = "0$locationId"

        val codprov = locationId.subSequence(0, locationId.length - 3).toString()
        val municipioResponse = getElTiempoService().getMunicipio(codprov, locationId)

        val horasHoy = municipioResponse.pronostico?.hoy?.viento?.map {
            it.attributes?.periodo
        }

        val horasManana = municipioResponse.pronostico?.manana?.viento?.map {
            it.attributes?.periodo
        }

        if (date == event.date.getAbsoluteDay()) {
            val strHora : String? = if (event.date.hora < 10) "0${event.date.hora}" else event.date.hora.toString()
            val index = horasHoy?.indexOf(strHora)
            val hoy = municipioResponse?.pronostico?.hoy

            if (index != -1) {
                proximo = ProximosDiasSingle(
                    attributes(horasHoy?.get(index!!), null, null, null),
                    hoy?.precipitacion?.get(index!!),
                    hoy?.estadoCielo?.get(index!!),
                    hoy?.viento?.get(index!!),
                    Temperatura(hoy?.temperatura?.get(index!!)),
                    SensTermica(hoy?.sensTermica?.get(index!!)),
                    HumedadRelativa(null, hoy?.humedadRelativa?.get(index!!)),
                    "No hay datos",
                    hoy?.estadoCieloDescripcion?.get(index!!)
                )
            }

        } else if (date + 1 == event.date.getAbsoluteDay()) {
            val strHora : String? = if (event.date.hora < 10) "0${event.date.hora}" else event.date.hora.toString()
            val index = horasManana?.indexOf(strHora)
            val manana = municipioResponse?.pronostico?.manana

            Log.d("INDICES", index.toString())
            if (index != -1) {
                proximo = ProximosDiasSingle(
                    attributes(horasManana?.get(index!!), null, null, null),
                    manana?.precipitacion?.get(index!!),
                    manana?.estadoCielo?.get(index!!),
                    manana?.viento?.get(index!!),
                    Temperatura(manana?.temperatura?.get(index!!)),
                    SensTermica(manana?.sensTermica?.get(index!!)),
                    HumedadRelativa(null, manana?.humedadRelativa?.get(index!!)),
                    "No hay datos",
                    manana?.estadoCieloDescripcion?.get(index!!)
                )
            }
        }

        Log.d("HORASHOY", horasHoy.toString() + horasManana.toString())
        return proximo
    }

    override fun onCiudadClick(ciudad: Ciudad) {
        val intent = Intent(this, PronosticoActivity::class.java)
        intent.putExtra(PronosticoActivity.CIUDAD, ciudad)
        intent.putExtra(PronosticoActivity.USER_INFO, user)
        startActivity(intent)
    }
}