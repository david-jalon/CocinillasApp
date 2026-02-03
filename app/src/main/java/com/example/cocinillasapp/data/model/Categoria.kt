package com.example.cocinillasapp.data.model

import com.google.gson.annotations.SerializedName

data class Categoria(
    @SerializedName("strCategory")
    val nombreCategoria: String,

    @SerializedName("strCategoryThumb")
    val imagenCategoria: String,

    @SerializedName("strCategoryDescription")
    val descripcionCategoria: String
)

data class CategoriasResponse(
    @SerializedName("categories")
    val listaCategorias: List<Categoria>
)