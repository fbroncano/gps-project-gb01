package es.unex.gps.weathevent.view.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import es.unex.gps.weathevent.WeathApplication
import es.unex.gps.weathevent.databinding.ActivityRegistroBinding
import es.unex.gps.weathevent.model.User
import es.unex.gps.weathevent.view.HomeActivity
import kotlinx.coroutines.launch


class RegistroActivity : AppCompatActivity() {

    private val viewModel: RegistroViewModel by viewModels {
        RegistroViewModel.provideFactory(
            (application as WeathApplication).appContainer.userRepository,
            (application as WeathApplication).appContainer,
            this)
    }

    private lateinit var binding: ActivityRegistroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpUi()
    }

    private fun setUpUi() {
        // Si todos los campos son correctos redirige a PerfilActivity
        binding.confirmarRegistro.setOnClickListener {
            lifecycleScope.launch {
                if (validate()) {
                    val user = User(
                        null,
                        binding.nombreRegistro.text.toString().trim(),
                        binding.usernameRegistro.text.toString().trim(),
                        binding.emailRegistro.text.toString().trim(),
                        binding.passRegistro.text.toString().trim()
                    )

                    val error = viewModel.register(user)
                    if (error == null) {
                        viewModel.setUser(user)
                        val intent = Intent(this@RegistroActivity, HomeActivity::class.java)
                        this@RegistroActivity.startActivity(intent)
                    } else {
                        changeError(binding.errorUserRegistro, error)
                    }
                }
            }
        }

        //Comprueba si se pulsa el Botón Iniciar Sesión para redirigir
        binding.inicarsesionRegistro.setOnClickListener {
            val intent = Intent(this, IniciarSesionActivity::class.java)
            startActivity(intent)
        }
    }

    private fun validate(): Boolean{
        val email = binding.emailRegistro.text.toString().trim()
        val name = binding.nombreRegistro.text.toString().trim()
        val username = binding.usernameRegistro.text.toString().trim()
        val password = binding.passRegistro.text.toString().trim()

        return changeError(binding.errorEmailRegistro, viewModel.validateEmail(email)) &&
                changeError(binding.errorNombre, viewModel.validateName(name)) &&
                changeError(binding.errorUserRegistro, viewModel.validateUsername(username)) &&
                changeError(binding.errorPassRegistro, viewModel.validatePassword(password))

    }

    private fun changeError(view: TextView, error: String?): Boolean {
        return if (error != null) {
            view.text = error
            view.visibility = View.VISIBLE
            false
        } else {
            view.visibility = View.INVISIBLE
            true
        }
    }
}