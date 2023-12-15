package es.unex.gps.weathevent.view.events

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputLayout
import es.unex.gps.weathevent.api.APIHelpers
import es.unex.gps.weathevent.database.WeathEventDataBase
import es.unex.gps.weathevent.databinding.ActivityAddEventBinding
import es.unex.gps.weathevent.model.Ciudad
import es.unex.gps.weathevent.model.Event
import es.unex.gps.weathevent.model.Fecha
import es.unex.gps.weathevent.model.toCiudad
import kotlinx.coroutines.launch
import java.text.Normalizer
import java.time.LocalDateTime

class AddEventActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAddEventBinding
    private var fecha = Fecha(0, 0, 0, 0, 0)

    private lateinit var name: TextInputLayout
    private lateinit var municipio: TextInputLayout
    private lateinit var dateView: TextInputLayout
    private lateinit var hourView: TextInputLayout
    private lateinit var errView: TextView

    private var municipios: List<Ciudad>? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        name = binding.eventNameView
        municipio = binding.locationView
        dateView = binding.dateView
        hourView = binding.hourView
        errView = binding.errorView

        binding.cancelEventBtn.setOnClickListener {
            finish()
        }

        binding.saveEventBtn.setOnClickListener {
            saveEvent()
        }

        binding.datePickerBtn.setOnClickListener {
            val c = Calendar.getInstance()

            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this,
                {_, chooseYear, monthOfYear, dayOfMonth ->
                    fecha.dia = dayOfMonth
                    fecha.mes = monthOfYear + 1
                    fecha.ano = chooseYear
                    dateView.editText?.setText("${fecha.dia}/${fecha.mes}/${fecha.ano}")
                }, year, month, day
            )

            datePickerDialog.show()
        }

        binding.hourPickerBtn.setOnClickListener {
            val time = LocalDateTime.now()

            val hourPickerDialog = TimePickerDialog(this,
                {_, hour, minutes ->
                    fecha.hora = hour
                    fecha.mins = minutes
                    hourView.editText?.setText("${fecha.hora}:${fecha.mins}")
                }, time.hour, time.minute, true
            )

            hourPickerDialog.show()
        }

        lifecycleScope.launch {
            municipios = APIHelpers().fetchMunicipios().map { it.toCiudad() }
        }
    }

    fun saveEvent() {
        val normalize: (String) -> String = {
            var str = Normalizer.normalize(it, Normalizer.Form.NFD)
            str.replace("[^\\p{ASCII}]", "")
        }

        var errorMsg : String = ""
        var ciudad : Ciudad? = null
        var valid = true
        var name = name.editText?.text.toString()
        var municipioStr = normalize(municipio.editText?.text.toString().trim())

        if (municipios != null) {
            if (name.length < 3) {
                errorMsg += "Se debe indicar un nombre de al menos tres caracteres\n"
                valid = false
            }

            val encontrados = municipios!!.filter {
                normalize(it.name).contains(municipioStr, ignoreCase = true)
            }

            // Filtramos si el municipio se ha encontrado
            if (encontrados.size == 1) {
                ciudad = encontrados[0]
                municipio.editText?.setText(ciudad.name)
            } else if (encontrados.isEmpty()) {
                errorMsg += "Revise la ortografía o indique un municipio\n"
                valid = false
            } else if (encontrados.size > 2) {
                val identicos = encontrados.filter {
                    normalize(it.name).equals(municipioStr, ignoreCase = true)
                }
                if (identicos.size == 1) {
                    ciudad = encontrados[0]
                    municipio.editText?.setText(ciudad.name)
                } else {
                    errorMsg += "Debe concretar más el nombre del municipio\n"
                    valid = false
                }
            }

            // Se comprueba que la fecha sea distinto de cero
            if (!fecha.isValid()) {
                valid = false
                errorMsg += "Debe introducir una fecha y una hora\n"
            }

            // Si es válido, se inserta y se retorna a la actividad anterior
            if (valid) {
                val event = Event(null, name, ciudad!!.name,fecha, 1, ciudad.ciudadId)
                val db = WeathEventDataBase.getInstance(this)

                lifecycleScope.launch {
                    db.eventDao().insertEvent(event)

                    finish()
                }
            } else {
                // Se muestran los errores
                errView.text = errorMsg
            }
        } else {
            Toast.makeText(this, "Cargando los datos de municipio. Espere un momento", Toast.LENGTH_LONG).show()
        }
    }

    companion object {
        val USER = "user"
    }
}