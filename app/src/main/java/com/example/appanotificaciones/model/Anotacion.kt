package com.example.appanotificaciones.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Anotacion")
data class Anotacion(@PrimaryKey(autoGenerate = true) var id:Long=0,
                     var tarea:String ="",
                     var Finalizado: Boolean=false){

}