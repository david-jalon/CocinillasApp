package com.example.cocinillasapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cocinillasapp.data.repository.RecetaRepository
import com.example.cocinillasapp.ui.DetalleViewModel

class DetalleViewModelFactory(private val repository: RecetaRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetalleViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DetalleViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}