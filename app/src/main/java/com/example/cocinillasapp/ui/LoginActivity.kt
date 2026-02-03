package com.example.cocinillasapp.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.Room
import com.example.cocinillasapp.R
import com.example.cocinillasapp.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
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

        //Comprobamos que no estén vacíos los textos
        if(user.isNotEmpty() && password.isNotEmpty()) {
            login(user, password)
        } else {
            mostrarErrorCamposVacios()
        }
    }

    private fun login(user: String, password: String) {
        // TODO val Apiservice

    }

    private fun irAcrearCuenta(){
        val intent = Intent(this, RegistroActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun irAListaRecetas(){
        /* TODO
        val intent = Intent(this, ListaRecetasActivity::class.java)
        startActivity(intent)
        finish()*/
    }

    private fun mostrarErrorCamposVacios(){
        val mensaje: String = "Por favor, rellene todos los campos"
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

    private fun mostrarErrorLogin(){
        val mensaje: String = "Usuario o contraseña incorrectos"
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

}


