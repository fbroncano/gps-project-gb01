package es.unex.gps.weathevent.view.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import es.unex.gps.weathevent.databinding.ActivityIniciarSesionBinding
import es.unex.gps.weathevent.view.HomeActivity
import kotlinx.coroutines.launch

class IniciarSesionActivity : AppCompatActivity() {

    private val viewModel: IniciarSesionViewModel by viewModels { IniciarSesionViewModel.Factory }
    private lateinit var binding: ActivityIniciarSesionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIniciarSesionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.entrar.setOnClickListener {
            loguear()
        }

        // Escucha cuando se pulsa el Botón que redirige a Iniciar Sesión
        binding.registro.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loguear() {
        lifecycleScope.launch {
            val error = viewModel.login(
                binding.userLogin.text.toString().trim(),
                binding.passLogin.text.toString().trim()
            )

            if (error != null) {
                binding.errorUserLogin.text = error
                binding.errorUserLogin.visibility = View.VISIBLE
            } else {
                if (viewModel.setUser()) {
                    val intent = Intent(this@IniciarSesionActivity, HomeActivity::class.java)
                    this@IniciarSesionActivity.startActivity(intent)
                } else {
                    binding.errorUserLogin.text = "No se ha podido iniciar sesión"
                    binding.errorUserLogin.visibility = View.VISIBLE
                }
            }
        }
    }

}