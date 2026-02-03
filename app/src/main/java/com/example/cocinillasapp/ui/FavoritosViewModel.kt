package com.example.cocinillasapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.cocinillasapp.data.model.Receta
import com.example.cocinillasapp.data.repository.RecetaRepository

class FavoritosViewModel(private val repository: RecetaRepository) : ViewModel() {

    fun getFavoritos(usuarioEmail: String): LiveData<List<Receta>> {
        return repository.getFavoritasPorUsuario(usuarioEmail)
    }
}