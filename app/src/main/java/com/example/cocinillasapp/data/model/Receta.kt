package com.example.cocinillasapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "recetas")
data class Receta(
    @SerializedName("idMeal") @PrimaryKey val id: String,
    @SerializedName("strMeal") val nombre: String,
    @SerializedName("strMealThumb") val imagen: String,
    @SerializedName("strInstructions") val descripcion: String?,
    @SerializedName("strCategory") val categoria: String

): Serializable

data class RecetaResponse(
    @SerializedName("meals") val listaRecetas: List<Receta>
)


