package com.example.cocinillasapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cocinillasapp.data.repository.RecetaRepository
import com.example.cocinillasapp.ui.FavoritosViewModel

class FavoritosViewModelFactory(private val repository: RecetaRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoritosViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavoritosViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}