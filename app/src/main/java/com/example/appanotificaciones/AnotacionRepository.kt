package com.example.appanotificaciones

import androidx.annotation.WorkerThread
import com.example.appanotificaciones.model.Anotacion
import kotlinx.coroutines.flow.Flow

//Solo necesitamos acceso al DAO, no a toda la base de datos
class AnotacionRepository(private val anotacionDao: AnotacionDAO) {

    val tareas: Flow<List<Anotacion>> = AnotacionDAO.getAlphabetizedtask()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(tarea: Anotacion) {
        AnotacionDAO.insert(tarea)
    }
}