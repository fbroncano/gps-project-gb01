package es.unex.gps.weathevent.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import es.unex.gps.weathevent.database.WeathEventDataBase
import es.unex.gps.weathevent.databinding.ActivityPerfilBinding
import es.unex.gps.weathevent.model.User
import kotlinx.coroutines.launch

class PerfilActivity : AppCompatActivity() {
    
    private lateinit var db: WeathEventDataBase
    private lateinit var user: User
    private lateinit var binding : ActivityPerfilBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicialización de la base de datos
        db = WeathEventDataBase.getInstance(applicationContext)

        // Obtener el usuario del Intent
        user = intent.getSerializableExtra("user") as User

        configurarInterfaz()

        binding.guardarButton.setOnClickListener {
            setEditable(false)
            binding.editarButton.visibility = View.VISIBLE
            binding.guardarButton.visibility = View.INVISIBLE

            // Actualizar usuario
            updateUser()

            configurarInterfaz()
        }

        binding.editarButton.setOnClickListener {
            setEditable(true)
            binding.currentPassword.editText?.setText(user.password)
            binding.guardarButton.visibility = View.VISIBLE
            binding.editarButton.visibility = View.INVISIBLE
        }

        // Configurar el texto de bienvenida
        binding.bienvenidaPerfil.text = "Hola, ${user.name}"
    }

    private fun User.mostrarInformacionLog() {
        Log.d("MyApp", "ID: $userId")
        Log.d("MyApp", "Nombre: $name")
        Log.d("MyApp", "Usuario: $username")
        Log.d("MyApp", "Email: $email")
        Log.d("MyApp", "PassWord: $password")
    }

    private fun updateUser() {
        val newUser = User(
            user.userId,
            binding.currentName.editText?.editableText.toString(),
            binding.currentUsername.editText?.editableText.toString(),
            binding.currentEmail.editText?.editableText.toString(),
            binding.currentPassword.editText?.editableText.toString()
        )

        user = newUser

        lifecycleScope.launch {
            db.userDao().updateUserData(
                newUser.userId,
                newUser.name,
                newUser.username,
                newUser.email,
                newUser.password
            )
        }

        binding.bienvenidaPerfil.text = "Hola, ${user.name}"
    }

    private fun configurarInterfaz() {
        binding.currentName.editText?.setText(user.name)
        binding.currentUsername.editText?.setText(user.username)
        binding.currentEmail.editText?.setText(user.email)
        binding.currentPassword.editText?.setText("*********")
    }

    private fun setEditable(enable : Boolean) {
        binding.currentName.editText?.isEnabled = enable
        binding.currentUsername.editText?.isEnabled = enable
        binding.currentEmail.editText?.isEnabled = enable
        binding.currentPassword.editText?.isEnabled = enable
    }
}