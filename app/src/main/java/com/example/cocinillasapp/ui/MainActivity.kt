package com.example.cocinillasapp.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cocinillasapp.R
import com.example.cocinillasapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Lista de recetas"

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        
        // Carga inicial de recetas
        fetchRecipes("Beef")
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
                // TODO: Implementar la carga de favoritos desde la base de datos local
                fetchRecipes("Favorites")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    
    private fun fetchRecipes(category: String) {
        supportActionBar?.title = "Categoría: ${'$'}category"
        // TODO: Aquí es donde llamarías a tu ViewModel para que obtenga las recetas.
        // Por ejemplo:
        // viewModel.getRecipesByCategory(category)
    }
}
