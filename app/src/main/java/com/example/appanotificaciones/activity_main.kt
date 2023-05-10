package com.example.appanotificaciones

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appanotificaciones.databinding.ActivityMainBinding
import com.example.appanotificaciones.model.Anotacion
import com.google.android.material.snackbar.Snackbar

class activity_main : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var anotacionAdapter: adapter_anotacion
    private lateinit var anotacionAdapterF: adapter_anotacion
    private lateinit var database:databaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //inicializar la clase
        database = databaseHelper(this)

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
                anota.id = database.insertTarea(anota)!!
                if(anota.id != constants.ID_ERROR)
                {
                    addAnotacion(anota)
                    binding.tvDescripcionTarea.text?.clear()
                    Snackbar.make(binding.root, "Se agrego una tarea", Snackbar.LENGTH_SHORT).show()
                }else{
                    Snackbar.make(binding.root, "Error al agregar tarea", Snackbar.LENGTH_SHORT).show()
                }
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
        val data = database.getTareas()
        data.forEach{ Anotacion->
            addAnotacion(Anotacion)
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
                if(database.deleteTarea(anota)){
                    adapters.remove(anota)
                    Snackbar.make(binding.root, "Se elimino correctamente", Snackbar.LENGTH_SHORT).show()
                }else{
                    Snackbar.make(binding.root, "Error", Snackbar.LENGTH_SHORT).show()

                }
        })
            .setNegativeButton(getString(R.string.strCancelar), null)
        builder.create().show()
    }

    override fun onChecked(anotacion: Anotacion) {
        if(database.updateTarea(anotacion)){
            deleteAnotacion(anotacion)
            addAnotacion(anotacion)
        }else{
            Snackbar.make(binding.root, "Ocurrio un error ala ctualizar", Snackbar.LENGTH_SHORT).show()
        }
    }
}

