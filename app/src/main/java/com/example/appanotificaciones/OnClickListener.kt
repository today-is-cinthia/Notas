package com.example.appanotificaciones

import com.example.appanotificaciones.model.Anotacion

interface OnClickListener {

    fun onChecked(anotacion: Anotacion){

    }

    fun onClick(anotacion: Anotacion, adapters: adapter_anotacion){

    }
}