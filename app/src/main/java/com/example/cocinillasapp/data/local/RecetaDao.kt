package com.example.cocinillasapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cocinillasapp.data.model.FavoritasUsuario
import com.example.cocinillasapp.data.model.Receta
import com.example.cocinillasapp.data.model.Usuario

@Dao
interface RecetaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsuario(usuario: Usuario)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertarFavorita(receta: Receta)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoritaUsuario(favoritasUsuario: FavoritasUsuario)

    @Query("SELECT recetas.* FROM recetas INNER JOIN favoritas_usuario ON recetas.id = favoritas_usuario.recetaId WHERE favoritas_usuario.usuarioEmail = :usuarioEmail")
    fun getFavoritasPorUsuario(usuarioEmail: String): LiveData<List<Receta>>

    @Query("DELETE FROM recetas WHERE id = :recetaId")
    suspend fun eliminarReceta(recetaId: String)

    @Query("DELETE FROM favoritas_usuario WHERE usuarioEmail = :usuarioEmail AND recetaId = :recetaId")
    suspend fun eliminarFavoritaDeUsuario(usuarioEmail: String, recetaId: String)

    @Query("SELECT * FROM usuarios WHERE email = :email AND password = :password")
    suspend fun autenticarUsuario(email: String, password: String): Usuario?

    @Query("SELECT * FROM recetas INNER JOIN favoritas_usuario ON recetas.id = favoritas_usuario.recetaId WHERE favoritas_usuario.usuarioEmail = :usuario AND favoritas_usuario.recetaId = :id LIMIT 1")
    suspend fun getRecetaByIdUsuario(usuario:String, id: String): Receta?

}