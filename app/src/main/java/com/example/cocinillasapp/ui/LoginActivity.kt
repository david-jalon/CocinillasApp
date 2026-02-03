package com.example.cocinillasapp.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.cocinillasapp.R
import com.example.cocinillasapp.data.local.AppDatabase
import com.example.cocinillasapp.data.remote.RetrofitClient
import com.example.cocinillasapp.data.repository.RecetaRepository
import com.example.cocinillasapp.databinding.ActivityLoginBinding
import com.example.cocinillasapp.viewmodel.RecetaViewModel
import com.example.cocinillasapp.viewmodel.RecetaViewModelFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: RecetaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // --- INICIALIZACIÓN CORRECTA DEL VIEWMODEL ---
        val database = AppDatabase.getDatabase(application)
        val repository = RecetaRepository(RetrofitClient.apiService, database.recetaDao())
        val factory = RecetaViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[RecetaViewModel::class.java]
        // ------------------------------------------

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnAcceder.setOnClickListener {
            hacerLogin()
        }

        binding.btnCrearCuenta.setOnClickListener {
            irAcrearCuenta()
        }
    }

    private fun hacerLogin() {
        val user: String = binding.etUsuario.text.toString().trim()
        val password: String = binding.etPassword.text.toString().trim()

        if (user.isNotEmpty() && password.isNotEmpty()) {
            viewModel.autenticarUsuario(user, password).observe(this) { usuario ->
                if (usuario != null) {
                    irAListaRecetas(usuario.email)
                } else {
                    mostrarErrorLogin()
                }
            }
        } else {
            mostrarErrorCamposVacios()
        }
    }

    private fun irAcrearCuenta() {
        val intent = Intent(this, RegistroActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun irAListaRecetas(usuario: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("usuario", usuario)
        startActivity(intent)
        finish()
    }

    private fun mostrarErrorCamposVacios() {
        val mensaje: String = "Por favor, rellene todos los campos"
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

    private fun mostrarErrorLogin() {
        val mensaje: String = "Usuario o contraseña incorrectos"
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}
