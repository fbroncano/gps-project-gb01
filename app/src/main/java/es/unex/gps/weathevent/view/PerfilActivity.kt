package es.unex.gps.weathevent.view

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import es.unex.gps.weathevent.databinding.ActivityPerfilBinding
import es.unex.gps.weathevent.model.User

class PerfilActivity : AppCompatActivity() {

    private lateinit var binding : ActivityPerfilBinding
    private val viewModel: PerfilViewModel by viewModels { PerfilViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPerfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.user.observe(this) {
            configurarInterfaz(it)
        }

        binding.guardarButton.setOnClickListener {
            setEditable(false)
            binding.editarButton.visibility = View.VISIBLE
            binding.guardarButton.visibility = View.INVISIBLE
            updateUser()
        }

        binding.editarButton.setOnClickListener {
            setEditable(true)
            binding.currentPassword.editText?.setText(viewModel.user.value?.password)
            binding.guardarButton.visibility = View.VISIBLE
            binding.editarButton.visibility = View.INVISIBLE
        }

        // Configurar el texto de bienvenida
        binding.bienvenidaPerfil.text = "Hola, ${viewModel.user.value?.name}"
    }

    private fun updateUser() {
        val newUser = User(
            viewModel.user.value?.userId,
            binding.currentName.editText?.editableText.toString(),
            binding.currentUsername.editText?.editableText.toString(),
            binding.currentEmail.editText?.editableText.toString(),
            binding.currentPassword.editText?.editableText.toString()
        )

        viewModel.updateUser(newUser)
        binding.bienvenidaPerfil.text = "Hola, ${viewModel.user.value?.name}"
    }

    private fun configurarInterfaz(user: User) {
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