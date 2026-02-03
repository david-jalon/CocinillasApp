package com.example.cocinillasapp.data.model

import androidx.room.Entity

@Entity(
    tableName = "favoritas_usuario",
    primaryKeys = ["usuarioEmail", "recetaId"],

)
data class FavoritasUsuario(
    val usuarioEmail: String,
    val recetaId: String // ID de la receta de la api
)
