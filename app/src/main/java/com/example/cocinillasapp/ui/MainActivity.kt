package com.example.cocinillasapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cocinillasapp.R
import com.example.cocinillasapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private lateinit var recetasAdapter: RecetasAdapter
    private var usuarioEmail: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        usuarioEmail = intent.getStringExtra("usuario")

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Lista de recetas"

        setupRecyclerView()

        viewModel.recetas.observe(this, Observer {
            recetasAdapter.updateRecetas(it)
        })

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Carga inicial de recetas
        fetchRecipes("Beef")
    }

    private fun setupRecyclerView() {
        recetasAdapter = RecetasAdapter(emptyList()) { receta ->
            val intent = Intent(this, DetalleActivity::class.java)
            intent.putExtra("receta_id", receta.id)
            intent.putExtra("usuario_email", usuarioEmail)
            startActivity(intent)
        }
        binding.rvRecetas.layoutManager = LinearLayoutManager(this)
        binding.rvRecetas.adapter = recetasAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.cat_beef -> {
                fetchRecipes("Beef")
                true
            }
            R.id.cat_pasta -> {
                fetchRecipes("Pasta")
                true
            }
            R.id.cat_pork -> {
                fetchRecipes("Pork")
                true
            }
            R.id.cat_chicken -> {
                fetchRecipes("Chicken")
                true
            }
            R.id.cat_seafood -> {
                fetchRecipes("Seafood")
                true
            }
            R.id.cat_favorites -> {
                val intent = Intent(this, FavoritosActivity::class.java)
                intent.putExtra("usuario_email", usuarioEmail)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun fetchRecipes(category: String) {
        supportActionBar?.title = "Categoría: $category"
        viewModel.getRecipesByCategory(category)
    }
}
