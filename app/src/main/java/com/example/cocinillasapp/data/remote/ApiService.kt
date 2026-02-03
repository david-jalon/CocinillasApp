package com.example.cocinillasapp.data.remote

import com.example.cocinillasapp.data.model.CategoriasResponse
import com.example.cocinillasapp.data.model.RecetaResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    // Obtener recetas por categoria
    @GET("filter.php")
    suspend fun getMealsByCategory(@Query("c") category: String): Response<RecetaResponse>

    // Obtener receta por ID
    @GET("lookup.php")
    suspend fun getMealById(@Query("i") id: Int): Response<RecetaResponse>

    // Obtener el listado de categorias para el Toolbar
    @GET("categories.php")
    suspend fun getCategories(): Response<CategoriasResponse>
    
}