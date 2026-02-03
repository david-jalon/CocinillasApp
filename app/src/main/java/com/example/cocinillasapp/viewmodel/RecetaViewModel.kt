package com.example.cocinillasapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.cocinillasapp.data.model.Receta
import com.example.cocinillasapp.data.model.Usuario
import com.example.cocinillasapp.data.repository.RecetaRepository
import kotlinx.coroutines.launch

class RecetaViewModel (private val repository: RecetaRepository): ViewModel() {

    private val _listaRecetasApi = MutableLiveData<List<Receta>>()
    val listaRecetasApi: LiveData<List<Receta>> = _listaRecetasApi


    fun obtenerRecetasPorCategoria(categoria: String){
        viewModelScope.launch {
            val respuesta = repository.buscarRecetas(categoria)
            if (respuesta.isSuccessful && respuesta.body() != null) {
                _listaRecetasApi.postValue(respuesta.body()!!.listaRecetas)
            }
        }
    }

    fun agregarUsuario(email: String, password: String) {
        viewModelScope.launch {
            repository.agregarUsuario(Usuario(email, password))
        }
    }

    fun autenticarUsuario(email: String, password: String): LiveData<Usuario?> = liveData {
        emit(repository.autenticarUsuario(email, password))
    }

    fun agregarFaoritaParaUsuario(usuarioEmail: String, receta: Receta) {
        viewModelScope.launch {
            repository.addFavorita(usuarioEmail, receta)
            getRecetasFavoritas(usuarioEmail)
        }
    }

    fun getRecetasFavoritas(usuarioEmail: String): LiveData<List<Receta>>? {
        return repository.getFavoritasPorUsuario(usuarioEmail)
    }

    fun eliminarFavoritaParaUsuario(usuarioEmail: String, recetaId: String) {
        viewModelScope.launch {
            repository.eliminarFavorita(usuarioEmail, recetaId)
            getRecetasFavoritas(usuarioEmail)
        }
    }

    fun esFavorita(usuarioEmail: String, id: String): LiveData<Boolean> = liveData {
        val recetaFavorita = repository.esFavorita(id, usuarioEmail)
        emit(recetaFavorita != null)
    }
}
