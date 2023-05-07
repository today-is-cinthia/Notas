package com.example.appanotificaciones

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appanotificaciones.databinding.ActivityMainBinding
import com.example.appanotificaciones.model.Anotacion

class activity_main : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var anotacionAdapter: adapter_anotacion
    private lateinit var anotacionAdapterF: adapter_anotacion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        anotacionAdapter = adapter_anotacion(mutableListOf(), this)
        binding.rvTareasPendientes.apply { layoutManager = LinearLayoutManager(this@activity_main)
        adapter = anotacionAdapter
        }

        //Tareas finalizaadas
        anotacionAdapterF = adapter_anotacion(mutableListOf(), this)
        binding.rvTareasFinalizadas.apply { layoutManager = LinearLayoutManager(this@activity_main)
            adapter = anotacionAdapterF
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

    override fun onStart() {
        super.onStart()
        getData()
    }

    private fun getData(){
        val data = mutableListOf(
            Anotacion(1,"Tarea AWS"),
            Anotacion(2,"Registrar notas"),
            Anotacion(3,"Desarrollar app Anotciones"),
            Anotacion(4,"Corregir avance laboral 6"),
            Anotacion(5,"Realizar pedido de toner"),
            Anotacion(6,"Continuara..."),
            Anotacion(7,"Seguimos ...", true)
        )
        data.forEach { Anotacionn ->
            addAnotacion(Anotacionn)
        }
    }

    private fun addAnotacion(anota: Anotacion) {
        if (anota.Finalizado)
        {
            anotacionAdapterF.add(anota)
        }
        else
        {
            anotacionAdapter.add(anota)
        }
    }

    private fun deleteAnotacion(anota: Anotacion) {
        if (anota.Finalizado)
        {
            anotacionAdapter.remove(anota)
        }
        else
        {
            anotacionAdapterF.remove(anota)
        }
    }

    override fun onClick(anota: Anotacion, adapters: adapter_anotacion){
        val builder = AlertDialog.Builder(this)
            .setTitle(getString(R.string.strDialogoTitulo))
            .setPositiveButton(getString(R.string.strAceptar),{
                dialogInterface, i ->
                adapters.remove(anota)
        })
            .setNegativeButton(getString(R.string.strCancelar), null)
        builder.create().show()
    }

    override fun onChecked(anotacion: Anotacion) {
        deleteAnotacion(anotacion)
        addAnotacion(anotacion)
    }
}

