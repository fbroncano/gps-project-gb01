package es.unex.gps.weathevent.view.events

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import es.unex.gps.weathevent.databinding.ActivityAddEventBinding
import es.unex.gps.weathevent.model.Fecha
import java.time.LocalDateTime

class AddEventActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAddEventBinding
    private var fecha = Fecha(0, 0, 0, 0, 0)

    private lateinit var name: TextInputLayout
    private lateinit var municipio: TextInputLayout
    private lateinit var dateView: TextInputLayout
    private lateinit var hourView: TextInputLayout
    private lateinit var errView: TextView

    private val viewModel: AddEventViewModel by viewModels { AddEventViewModel.Factory }

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
                    val diaStr = if (dayOfMonth < 10) "0$dayOfMonth" else dayOfMonth
                    val mesStr = if (fecha.mes < 10) "0${fecha.mes}" else fecha.mes
                    dateView.editText?.setText("${diaStr}/${mesStr}/${fecha.ano}")
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
                    val horaStr = if (hour < 10) "0$hour" else hour
                    val minsStr = if (minutes < 10) "0$minutes" else minutes
                    hourView.editText?.setText("${horaStr}:${minsStr}")
                }, time.hour, time.minute, true
            )

            hourPickerDialog.show()
        }
    }

    private fun saveEvent() {

        var errorMsg = ""
        var error: String?

        var valid = true
        val name = name.editText?.text.toString()
        val municipioStr = municipio.editText?.text.toString().trim()

        // Comprobar que el nombre del evento sea correcto
        error = viewModel.validateName(name)
        if (error != null) {
            errorMsg += "Se debe indicar un nombre de al menos tres caracteres\n"
            valid = false
        }

        // Comprobar que el municipio sea correcto
        error = viewModel.filterMunicipio(municipioStr)
        if (error != null) {
            errorMsg += error
            valid = false
        }

        // Se comprueba que la fecha sea correcta
        error = viewModel.validateFecha(fecha)
        if (error != null) {
            errorMsg += error
            valid = false
        }

        // Si es válido, se inserta y se retorna a la actividad anterior
        if (valid) {
            if (!viewModel.insertEvent(name, fecha)) {
                errView.text = "No se ha podido insertar el evento."
            }
        } else {
            // Se muestran los errores
            errView.text = errorMsg
        }
    }
}