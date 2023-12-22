package es.unex.gps.weathevent.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import es.unex.gps.weathevent.api.APIHelpers
import es.unex.gps.weathevent.data.api.ProximosDias
import es.unex.gps.weathevent.data.api.ProximosDiasArray
import es.unex.gps.weathevent.data.api.ProximosDiasSingle
import es.unex.gps.weathevent.databinding.ActivityEventDetailsBinding
import es.unex.gps.weathevent.model.Event

class EventDetailsActivity : AppCompatActivity() {

    private lateinit var binding : ActivityEventDetailsBinding
    private var event : Event? = null
    private var proximoDia : ProximosDias? = null
    private var pronostico : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        event = intent.getSerializableExtra(EVENT) as? Event
        proximoDia = intent.getSerializableExtra(PREDICCION) as? ProximosDias
        pronostico = intent.getBooleanExtra(PRONOSTICO, false)

        Log.d("APIResult", proximoDia.toString())

        binding.exportButton.setOnClickListener {
            exportEvent()
        }

        updateUI()
    }

    private fun updateUI() {
        binding.ubicationText.text = event?.location
        binding.dateView.text = event?.date?.getFormatDay()
        binding.eventDataView.text = "${event?.date?.getFormatHour()} - ${event?.name}"

        if (pronostico && proximoDia is ProximosDiasSingle) {
            setPronostico(proximoDia as ProximosDiasSingle)
        } else if (proximoDia is ProximosDiasSingle) {
            setPronostico(proximoDia as ProximosDiasSingle)
        } else if (proximoDia is ProximosDiasArray) {
            setProximoDia(proximoDia as ProximosDiasArray)
        } else {
            binding.temperatureView.text = "No hay datos disponibles"
        }
    }
    private fun setPronostico(proximoDia: ProximosDiasSingle) {
        binding.temperatureView.text = APIHelpers.convertTempToPreferences(proximoDia.temperatura?.maxima?.toLong()!!, this)
        binding.precipitacionValueView.text = "${proximoDia.probPrecipitacion}mm"
        binding.sendtermValueView.text = APIHelpers.convertTempToPreferences(proximoDia.sensTermica?.maxima?.toLong()!!, this)
        binding.estadoValueView.text = proximoDia.estadoCieloDescripcion
        binding.vientoValueView.text = "${APIHelpers.convertVelToPreferences(proximoDia.viento?.velocidad?.toLong()!!, this)} ${proximoDia.viento?.direccion}"
        binding.indiceuvValueView.text = "${proximoDia.uvMax}"
        binding.humedadValueView.text = "${proximoDia.humedadRelativa?.minima}%"
    }

    private fun setProximoDia(proximoDia : ProximosDiasArray) {
        binding.temperatureView.text = "${APIHelpers.convertTempToPreferences(proximoDia.temperatura?.minima?.toLong()!!, this)} - ${APIHelpers.convertTempToPreferences(proximoDia.temperatura?.maxima?.toLong()!!, this)}"
        binding.precipitacionValueView.text = "${proximoDia.probPrecipitacion[0]}%"
        binding.sendtermValueView.text = "${APIHelpers.convertTempToPreferences(proximoDia.sensTermica?.minima?.toLong()!!, this)} - ${APIHelpers.convertTempToPreferences(proximoDia.sensTermica?.maxima?.toLong()!!, this)}"
        binding.estadoValueView.text = proximoDia.estadoCieloDescripcion[0]
        binding.vientoValueView.text = "${APIHelpers.convertVelToPreferences(proximoDia.viento[0]?.velocidad?.toLong()!!, this)}km/h ${proximoDia.viento[0]?.direccion}"
        binding.indiceuvValueView.text = "${proximoDia.uvMax}"
        binding.humedadValueView.text = "${proximoDia.humedadRelativa?.minima}%"
    }

    private fun exportEvent() {
        val gson = Gson()
        val json = gson.toJson(event)

        // Crear un Intent para compartir
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, json)
            type = "text/plain"
        }

        // Mostrar las aplicaciones disponibles para compartir
        val shareIntent = Intent.createChooser(sendIntent, "Evento")
        startActivity(shareIntent)
    }

    companion object {
        val EVENT = "Event"
        val PREDICCION = "Prediccion"
        val PRONOSTICO = "Pronostico"
    }
}