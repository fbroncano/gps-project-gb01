package es.unex.gps.weathevent.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import es.unex.gps.weathevent.R
import es.unex.gps.weathevent.database.WeathEventDataBase
import es.unex.gps.weathevent.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class IniciarSesionActivity : AppCompatActivity() {

    private lateinit var db: WeathEventDataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_iniciar_sesion)

        db = WeathEventDataBase.getInstance(applicationContext)

        // Comprueba que se cumpla cada uno de los tipos de los campos
        val user = findViewById<EditText>(R.id.userLogin)
        val btnEntrar = findViewById<Button>(R.id.entrar)

        btnEntrar.setOnClickListener {
            val username = user.text.toString().trim()
            val errorUser = findViewById<TextView>(R.id.errorUserLogin)
            var user: User?

            if (!username.contains(" ") && !username.replace(" ", "").equals("")) {
                lifecycleScope.launch {
                   // try {
                        withContext(Dispatchers.IO) {
                            user = db.userDao().findByUsername(username)
                        }

                        if (user != null) {
                            errorUser.visibility = View.INVISIBLE

                            if (validatePassword(user!!)) {
                                loguear(user!!)
                            }
                        } else {
                            errorUser.text = "El usuario no existe"
                            errorUser.visibility = View.VISIBLE
                        }
                    /*} catch (e: Exception) {
                        Toast.makeText(
                            applicationContext,
                            "Error al acceder a la Base de Datos",
                            Toast.LENGTH_SHORT
                        ).show()
                    }*/
                }
            } else {
                errorUser.text = "El usuario no puede contener espacios"
                errorUser.visibility = View.VISIBLE
            }
        }

        // Escucha cuando se pulsa el Botón que redirige a Iniciar Sesión
        val btnRegistroActivity = findViewById<Button>(R.id.registro)
        btnRegistroActivity.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }
    }

/*
    private fun loguear(user: User) {
        val intentPerfilActivity = Intent(this, PerfilActivity::class.java)
        intentPerfilActivity.putExtra("user", user)
        startActivity(intentPerfilActivity)
    }*/

    private fun loguear(user: User) {
        MainActivity.start(this, user)
    }

    private fun validatePassword(user: User): Boolean {
        val password = findViewById<EditText>(R.id.passLogin).text.toString().trim()
        val errorPass = findViewById<TextView>(R.id.errorPassLogin)

        return if (password.isEmpty()) {
            Toast.makeText(this, "El campo de contraseña no puede estar vacío", Toast.LENGTH_SHORT).show()
            false
        } else if (password.replace(" ", "").equals("") || password.contains(" ") || password != user.password){
            errorPass.text = "Contraseña incorrecta"
            errorPass.visibility = View.VISIBLE
            false
        }else {
            errorPass.visibility = View.INVISIBLE
            true
        }
    }
}