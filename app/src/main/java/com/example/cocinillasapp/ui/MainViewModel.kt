package com.example.cocinillasapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocinillasapp.data.model.Receta
import com.example.cocinillasapp.data.remote.RetrofitClient
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _recetas = MutableLiveData<List<Receta>>()
    val recetas: LiveData<List<Receta>> = _recetas

    fun getRecipesByCategory(category: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.getMealsByCategory(category)
                if (response.isSuccessful) {
                    _recetas.value = response.body()?.listaRecetas
                } else {
                    // Handle error
                    _recetas.value = emptyList() // or post an error state
                }
            } catch (e: Exception) {
                // Handle exception
                _recetas.value = emptyList() // or post an error state
            }
        }
    }
}
