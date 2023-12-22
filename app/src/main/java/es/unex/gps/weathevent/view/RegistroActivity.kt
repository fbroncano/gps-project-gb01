package es.unex.gps.weathevent.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import es.unex.gps.weathevent.R
import es.unex.gps.weathevent.database.WeathEventDataBase
import es.unex.gps.weathevent.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegistroActivity : AppCompatActivity() {

    private lateinit var db: WeathEventDataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        db = WeathEventDataBase.getInstance(applicationContext)

        val btnCrearCuenta = findViewById<Button>(R.id.confirmarRegistro)
        val iniciarSesionButton = findViewById<Button>(R.id.inicarsesionRegistro)

        // Si todos los campos son correctos redirige a PerfilActivity
        btnCrearCuenta.setOnClickListener {

            lifecycleScope.launch {
                val userErrorRegistro = findViewById<TextView>(R.id.errorUserRegistro)
                val username = findViewById<TextView>(R.id.usernameRegistro).text.toString().trim()
                var existeUser: User?
                withContext(Dispatchers.IO) {
                    existeUser = db.userDao().findByUsername(username)
                }
                if(existeUser == null){
                    if (todoValido()) {
                        val user = User(
                            null,
                            findViewById<EditText>(R.id.nombreRegistro).text.toString().trim(),
                            username,
                            findViewById<EditText>(R.id.emailRegistro).text.toString().trim(),
                            findViewById<EditText>(R.id.passRegistro).text.toString().trim()
                        )
                        registrar(user)
                    }
                }else{
                    userErrorRegistro.text = "El nombre de usuario ya existe"
                    userErrorRegistro.visibility = View.VISIBLE
                }
            }
        }

        //Comprueba si se pulsa el Botón Iniciar Sesión para redirigir
        iniciarSesionButton.setOnClickListener {
            val intent = Intent(this, IniciarSesionActivity::class.java)
            startActivity(intent)
        }

    }

    private suspend fun registrar(user: User){
        val context = this
        withContext(Dispatchers.IO) {
            val id = db.userDao().insert(user)
            MainActivity.start(context, User(id, user.name, user.username, user.email, user.password))
        }
    }

    private fun todoValido(): Boolean{
        var valido = true

        valido = validarNombre() && valido
        valido = validarUsuario() && valido
        valido = validarEmail() && valido
        valido = validarContrasena() && valido

        return valido
    }

    private fun validarNombre(): Boolean {
        val name = findViewById<EditText>(R.id.nombreRegistro).text.toString().trim()
        val nombreErrorRegistro = findViewById<TextView>(R.id.errorNombre)
        if (name.isEmpty()) {
            nombreErrorRegistro.text = "El nombre no puede estar vacío."
            nombreErrorRegistro.visibility = View.VISIBLE
            return false
        } else {
            nombreErrorRegistro.visibility = View.INVISIBLE
            return true
        }
    }

    private fun validarUsuario(): Boolean {
        val username = findViewById<EditText>(R.id.usernameRegistro).text.toString().trim()
        val usernameErrorRegistro = findViewById<TextView>(R.id.errorUserRegistro)

        if (username.isEmpty()) {
            usernameErrorRegistro.text = "El usuario no puede estar vacío."
            usernameErrorRegistro.visibility = View.VISIBLE
            return false
        } else if (username.contains(" ")) {
            usernameErrorRegistro.text = "No puede contener espacios en blanco."
            usernameErrorRegistro.visibility = View.VISIBLE
            return false
        } else {
            usernameErrorRegistro.visibility = View.INVISIBLE
            return true
        }

    }

    private fun validarEmail(): Boolean {
        val email = findViewById<EditText>(R.id.emailRegistro).text.toString().trim()
        val emailErrorRegistro = findViewById<TextView>(R.id.errorEmailRegistro)
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailErrorRegistro.text = "El correo electrónico no es válido."
            emailErrorRegistro.visibility = View.VISIBLE
            return false
        } else {
            emailErrorRegistro.visibility = View.INVISIBLE
            return true
        }
    }

    private fun validarContrasena(): Boolean {
        val password = findViewById<EditText>(R.id.passRegistro).text.toString().trim()
        val passErrorRegistro = findViewById<TextView>(R.id.errorPassRegistro)
        if (password.isEmpty()) {
            passErrorRegistro.text = "La contraseña no puede estar vacía."
            passErrorRegistro.visibility = View.VISIBLE
            return false
        } else if (password.contains(" ")) {
            passErrorRegistro.text = "No puede contener espacios en blanco."
            passErrorRegistro.visibility = View.VISIBLE
            return false
        } else {
            passErrorRegistro.visibility = View.INVISIBLE
            return true
        }
    }
}