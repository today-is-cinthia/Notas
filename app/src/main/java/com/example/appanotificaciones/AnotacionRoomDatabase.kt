package com.example.appanotificaciones

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.appanotificaciones.model.Anotacion

//Agrega una clase como base de datos room con una tabla anotacion
// Ahorra hacer un databaseHelper
@Database(entities = arrayOf(Anotacion::class), version = 1, exportSchema = false)
public abstract class AnotacionRoomDatabase: RoomDatabase() {

    abstract fun anotacionDao(): AnotacionDAO

    companion object {
        //Previene multiples instancias de la base de datos al mismo tiempo
        @Volatile
        private var INSTANCE: AnotacionRoomDatabase? = null

        fun getDatabase(context: Context): AnotacionRoomDatabase {
            // Si la instancia no es null (ya existe la base de datos), se retorna
            // De otra manera se crea
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AnotacionRoomDatabase::class.java,
                    "Anotacion"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

    }

}