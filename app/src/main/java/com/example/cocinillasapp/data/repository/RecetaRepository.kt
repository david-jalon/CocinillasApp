package com.example.cocinillasapp.data.repository

import androidx.lifecycle.LiveData
import com.example.cocinillasapp.data.local.RecetaDao
import com.example.cocinillasapp.data.model.FavoritasUsuario
import com.example.cocinillasapp.data.model.Receta
import com.example.cocinillasapp.data.model.Usuario
import com.example.cocinillasapp.data.remote.ApiService

class RecetaRepository(private val apiService: ApiService, private val favoritaDao: RecetaDao) {

    suspend fun buscarRecetas(category: String) = apiService.getMealsByCategory(category)

    suspend fun getMealById(id: String) = apiService.getMealById(id)

    suspend fun agregarUsuario(usuario: Usuario){
        favoritaDao.insertUsuario(usuario)
    }

    suspend fun autenticarUsuario(email: String, password: String): Usuario? {
        return favoritaDao.autenticarUsuario(email, password)
    }

    fun getFavoritasPorUsuario(usuarioEmail: String): LiveData<List<Receta>> {
        return favoritaDao.getFavoritasPorUsuario(usuarioEmail)
    }

    suspend fun eliminarFavorita(usuarioEmail: String, recetaId: String){
        favoritaDao.eliminarFavoritaDeUsuario(usuarioEmail, recetaId)
    }

    suspend fun esFavorita(id: String, usuarioEmail: String): Receta? {
        return favoritaDao.getRecetaByIdUsuario(usuarioEmail, id)
    }

    suspend fun addFavorita(usuario: String, receta: Receta){
        favoritaDao.insertarFavorita(receta)
        favoritaDao.insertFavoritaUsuario(FavoritasUsuario(usuario, receta.id))
    }
}