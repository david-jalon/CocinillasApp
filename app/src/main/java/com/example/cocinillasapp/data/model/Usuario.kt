package com.example.cocinillasapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "usuarios")
data class Usuario(
    @PrimaryKey val email: String, // Usamos el email como clave primaria
    val password: String // Contraseña asociada al usuario
)
