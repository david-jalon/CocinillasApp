package com.example.cocinillasapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocinillasapp.data.model.Receta
import com.example.cocinillasapp.data.repository.RecetaRepository
import kotlinx.coroutines.launch

class DetalleViewModel(private val repository: RecetaRepository) : ViewModel() {

    private val _receta = MutableLiveData<Receta?>()
    val receta: LiveData<Receta?> = _receta

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean> = _isFavorite

    fun getRecipeDetails(id: String) {
        viewModelScope.launch {
            try {
                // Esta llamada a la API no la tienes en tu repo, la crearé ahora
                val response = repository.getMealById(id)
                if (response.isSuccessful) {
                    _receta.value = response.body()?.listaRecetas?.firstOrNull()
                } else {
                    _receta.value = null
                }
            } catch (e: Exception) {
                _receta.value = null
            }
        }
    }

    fun checkFavoriteStatus(recetaId: String, usuarioEmail: String) {
        viewModelScope.launch {
            _isFavorite.value = repository.esFavorita(recetaId, usuarioEmail) != null
        }
    }

    fun toggleFavorite(receta: Receta, usuarioEmail: String) {
        viewModelScope.launch {
            if (_isFavorite.value == true) {
                repository.eliminarFavorita(usuarioEmail, receta.id)
                _isFavorite.value = false
            } else {
                repository.addFavorita(usuarioEmail, receta)
                _isFavorite.value = true
            }
        }
    }
}
