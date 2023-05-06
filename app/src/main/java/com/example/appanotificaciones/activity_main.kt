package com.example.appanotificaciones

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appanotificaciones.databinding.ActivityMainBinding
import com.example.appanotificaciones.model.Anotacion

class activity_main : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var anotacionAdapter: adapter_anotacion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = mutableListOf(
            Anotacion(1,"Tarea AWS"),
            Anotacion(2,"Registrar notas"),
            Anotacion(3,"Desarrollar app Anotciones"),
            Anotacion(4,"Corregir avance laboral 6"),
            Anotacion(5,"Realizar pedido de toner"),
            Anotacion(6,"Continuara..."),
            Anotacion(7,"Seguimos ...", true)
        )
        anotacionAdapter = adapter_anotacion(data, this)
        binding.rvAnotaciones.apply { layoutManager = LinearLayoutManager(this@activity_main)
        adapter = anotacionAdapter
        }

        binding.btnAgregar.setOnClickListener {
            if(binding.tvDescripcionTarea.text.toString().isNotBlank()){
                val anota = Anotacion((anotacionAdapter.itemCount + 1).toLong(),
                                       binding.tvDescripcionTarea.text.toString().trim())
                addAnotacion(anota)
                binding.tvDescripcionTarea.text?.clear()
            }else
            {
                binding.tvDescripcionTarea.error = getString(R.string.strValidacionError)
            }
        }
    }

    private fun addAnotacion(anota: Anotacion) {
        anotacionAdapter.add(anota)
    }

    private fun deliteAnotacion(anota: Anotacion) {
        anotacionAdapter.remove(anota)
    }

    override fun onClick(anota: Anotacion){
        deliteAnotacion(anota)
    }
}

