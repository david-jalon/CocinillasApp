package com.example.cocinillasapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.cocinillasapp.data.model.FavoritasUsuario
import com.example.cocinillasapp.data.model.Receta
import com.example.cocinillasapp.data.model.Usuario

@Database(entities = [Receta::class, Usuario::class, FavoritasUsuario::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase(){
    abstract fun recetaDao(): RecetaDao

    companion object{
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase{
            val tempInstance: AppDatabase? = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, name = "recetas_db"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}


