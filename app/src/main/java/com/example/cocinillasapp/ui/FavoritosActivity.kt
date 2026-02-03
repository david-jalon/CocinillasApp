package com.example.cocinillasapp.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cocinillasapp.R
import com.example.cocinillasapp.data.local.AppDatabase
import com.example.cocinillasapp.data.remote.RetrofitClient
import com.example.cocinillasapp.data.repository.RecetaRepository
import com.example.cocinillasapp.databinding.ActivityFavoritosBinding
import com.example.cocinillasapp.viewmodel.FavoritosViewModelFactory

class FavoritosActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoritosBinding
    private lateinit var viewModel: FavoritosViewModel
    private lateinit var recetasAdapter: RecetasAdapter
    private var usuarioEmail: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityFavoritosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        usuarioEmail = intent.getStringExtra("usuario_email")

        if (usuarioEmail == null) {
            finish()
            return
        }

        setupToolbar()
        setupViewModel()
        setupRecyclerView()
        setupObservers()

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbarFavoritos)
        supportActionBar?.title = "Mis Favoritos"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbarFavoritos.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
    }

    private fun setupViewModel() {
        val database = AppDatabase.getDatabase(application)
        val repository = RecetaRepository(RetrofitClient.apiService, database.recetaDao())
        val factory = FavoritosViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[FavoritosViewModel::class.java]
    }

    private fun setupRecyclerView() {
        recetasAdapter = RecetasAdapter(emptyList()) { receta ->
            val intent = Intent(this, DetalleActivity::class.java)
            intent.putExtra("receta_id", receta.id)
            intent.putExtra("usuario_email", usuarioEmail)
            startActivity(intent)
        }
        binding.rvFavoritos.layoutManager = LinearLayoutManager(this)
        binding.rvFavoritos.adapter = recetasAdapter
    }

    private fun setupObservers() {
        usuarioEmail?.let { email ->
            viewModel.getFavoritos(email).observe(this, Observer { recetas ->
                recetasAdapter.updateRecetas(recetas)
            })
        }
    }
}
