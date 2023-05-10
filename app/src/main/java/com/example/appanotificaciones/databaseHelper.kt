package com.example.appanotificaciones

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.content.contentValuesOf
import com.example.appanotificaciones.model.Anotacion
import java.util.function.BinaryOperator

class databaseHelper(context: Context): SQLiteOpenHelper(context, constants.DATABASE_NAME, null, constants.DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase?)
    {

        val createTable = "CREATE TABLE ${constants.ENTITY_TAREA} (" + "${constants.PROPERTY_ID} INTEGER PRIMARY KEY AUTOINCREMENT, " + "${constants.PROPERTY_DESCRIPCION} VARCHAR(120), " + " ${constants.PROPERTY_FINALIZADO} BOOLEAN)"

        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    @SuppressLint("Range")
    fun getTareas(): MutableList<Anotacion>{

        val tareas: MutableList<Anotacion> = mutableListOf()
        val database = this.readableDatabase
        val query = "SELECT * FROM ${constants.ENTITY_TAREA}"
        val result = database.rawQuery(query, null)

        if(result.moveToFirst())
        {
            do {
                val tarea = Anotacion()
                tarea.id = result.getLong(result.getColumnIndex(constants.PROPERTY_ID))
                tarea.tarea = result.getString(result.getColumnIndex(constants.PROPERTY_DESCRIPCION))
                tarea.Finalizado = result.getInt(result.getColumnIndex(constants.PROPERTY_FINALIZADO)) == constants.TRUE
                tareas.add(tarea)
            }while (result.moveToNext())
        }
        return tareas
    }

    fun insertTarea(anota:Anotacion): Long?{
        val database = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(constants.PROPERTY_DESCRIPCION, anota.tarea)
            put(constants.PROPERTY_FINALIZADO, anota.Finalizado)
        }
        val resultId = database.insert(constants.ENTITY_TAREA, null, contentValues)
       return resultId
        /** val result = database.update(
            constants.ENTITY_TAREA,
            contentValues,
            "${constants.PROPERTY_ID} = ${anota.id}",
            null
        )**/
    }

    fun updateTarea(anota: Anotacion): Boolean{
        val database = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(constants.PROPERTY_DESCRIPCION, anota.tarea)
            put(constants.PROPERTY_FINALIZADO, anota.Finalizado)
        }
        val result = database.update(
            constants.ENTITY_TAREA,
            contentValues,
            "${constants.PROPERTY_ID} = ${anota.id}",
            null)
        return result == constants.TRUE
    }

    fun deleteTarea(anota: Anotacion): Boolean{
        val database = this.writableDatabase

        val result = database.delete(
            constants.ENTITY_TAREA,
            "${constants.PROPERTY_ID} = ${anota.id}",
            null)
        return result == constants.TRUE
    }
}