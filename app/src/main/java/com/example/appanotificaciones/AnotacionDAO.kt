package com.example.appanotificaciones

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appanotificaciones.model.Anotacion
import kotlinx.coroutines.flow.Flow

//Objeto que nos permite asociar queries con llamadas a metodos
@Dao
interface AnotacionDAO {
    @Query("SELECT * FROM Anotacion ORDER BY tarea ASC")

    fun getAlphabetizedtask(): Flow<List<Anotacion>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(tarea: Anotacion)

    @Query("DELETE FROM Anotacion")
    suspend fun deleteAll()
}