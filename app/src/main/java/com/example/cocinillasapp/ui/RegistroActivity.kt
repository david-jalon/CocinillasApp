package com.example.cocinillasapp.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.cocinillasapp.data.local.AppDatabase
import com.example.cocinillasapp.data.remote.RetrofitClient
import com.example.cocinillasapp.databinding.ActivityRegistroBinding
import com.example.cocinillasapp.data.repository.RecetaRepository
import com.example.cocinillasapp.viewmodel.RecetaViewModel
import com.example.cocinillasapp.viewmodel.RecetaViewModelFactory

class RegistroActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroBinding
    private lateinit var viewModel: RecetaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val apiService = RetrofitClient.apiService
        val database = AppDatabase.getDatabase(this)
        val repository = RecetaRepository(apiService,database.recetaDao())
        val factory = RecetaViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(RecetaViewModel::class.java)
        binding.btnCrearCuenta.setOnClickListener {
            comprobarDatosyRegistrarUsuario()
        }
    }

    private fun comprobarDatosyRegistrarUsuario(){
        val user: String = binding.etUsuario.text.toString().trim()
        val password: String = binding.etPassword.text.toString().trim()
        val confirmPassword: String = binding.etConfirmPassword.text.toString().trim()

        val isAnyFieldEmpty: Boolean = !(user.isEmpty() || password.isEmpty() || confirmPassword.isEmpty())
        val arePasswordsTheSame: Boolean = password == confirmPassword

        if(isAnyFieldEmpty && arePasswordsTheSame){
            guardarUsuario(user, password)
            irALogin()
        }else if (!arePasswordsTheSame){
            mostrarErrorPassword()
        }else{
            mostrarErrorCamposVacios()
        }
    }

    private fun guardarUsuario(user:String, password:String){
        viewModel.agregarUsuario(email=user, password = password)
        mostrarMensajeUsuarioGuardado()
    }

    private fun mostrarMensajeUsuarioGuardado(){
        val mensaje: String = "Usuario guardado correctamente"
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

    private fun irALogin(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun mostrarErrorPassword(){
        val mensaje: String = "Las contraseñas no coinciden"
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

    private fun mostrarErrorCamposVacios(){
        val mensaje: String = "Por favor, rellene todos los campos"
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}