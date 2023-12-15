package es.unex.gps.weathevent.view.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import es.unex.gps.weathevent.R
import es.unex.gps.weathevent.database.WeathEventDataBase
import es.unex.gps.weathevent.databinding.ActivityIniciarSesionBinding
import es.unex.gps.weathevent.model.User
import es.unex.gps.weathevent.view.home.HomeActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class IniciarSesionActivity : AppCompatActivity() {

    private val viewModel: IniciarSesionViewModel by viewModels { IniciarSesionViewModel.Factory }
    private lateinit var binding: ActivityIniciarSesionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIniciarSesionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.entrar.setOnClickListener {
            lifecycleScope.launch {
                val error = viewModel.login(
                    binding.userLogin.text.toString().trim(),
                    binding.passLogin.text.toString().trim()
                )

                if (error != null) {
                    binding.errorUserLogin.text = error
                    binding.errorUserLogin.visibility = View.VISIBLE
                } else {
                    loguear(viewModel.user!!)
                }
            }
        }

        // Escucha cuando se pulsa el Botón que redirige a Iniciar Sesión
        binding.registro.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loguear(user: User) {
        HomeActivity.start(this, user)
    }

}