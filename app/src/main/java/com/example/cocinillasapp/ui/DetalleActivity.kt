package com.example.cocinillasapp.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.cocinillasapp.R
import com.example.cocinillasapp.data.local.AppDatabase
import com.example.cocinillasapp.data.model.Receta
import com.example.cocinillasapp.data.remote.RetrofitClient
import com.example.cocinillasapp.data.repository.RecetaRepository
import com.example.cocinillasapp.databinding.ActivityDetalleBinding
import com.example.cocinillasapp.viewmodel.DetalleViewModelFactory
import com.squareup.picasso.Picasso

class DetalleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetalleBinding
    private lateinit var viewModel: DetalleViewModel
    private var currentReceta: Receta? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityDetalleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recetaId = intent.getStringExtra("receta_id")
        val usuarioEmail = intent.getStringExtra("usuario_email")

        if (recetaId == null || usuarioEmail == null) {
            // Manejar el error, por ejemplo, cerrar la actividad
            finish()
            return
        }

        // --- Configuración del ViewModel con la Factory ---
        val database = AppDatabase.getDatabase(application)
        val repository = RecetaRepository(RetrofitClient.apiService, database.recetaDao())
        val factory = DetalleViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[DetalleViewModel::class.java]
        // ---------------------------------------------------

        viewModel.getRecipeDetails(recetaId)
        viewModel.checkFavoriteStatus(recetaId, usuarioEmail)

        setupObservers(usuarioEmail)
        setupFavoriteButton(usuarioEmail)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupObservers(usuarioEmail: String) {
        viewModel.receta.observe(this, Observer { receta ->
            receta?.let {
                currentReceta = it
                setupToolbar(it.nombre)
                displayReceta(it)
            }
        })

        viewModel.isFavorite.observe(this, Observer { isFavorite ->
            val favoriteIcon = if (isFavorite) R.drawable.ic_favorito_lleno else R.drawable.ic_favorito_vacio
            binding.btnFavoritos.setImageDrawable(ContextCompat.getDrawable(this, favoriteIcon))
        })
    }

    private fun setupFavoriteButton(usuarioEmail: String) {
        binding.btnFavoritos.setOnClickListener {
            currentReceta?.let {
                viewModel.toggleFavorite(it, usuarioEmail)
            }
        }
    }

    private fun setupToolbar(title: String) {
        setSupportActionBar(binding.toolbarDetalle)
        supportActionBar?.title = title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarDetalle.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
    }

    private fun displayReceta(receta: Receta) {
        binding.tvTitulo.text = receta.nombre
        binding.tvInstrucciones.text = receta.descripcion

        Picasso.get()
            .load(receta.imagen)
            .into(binding.ivReceta)
    }
}
