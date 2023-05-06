package com.example.appanotificaciones

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.appanotificaciones.databinding.ItemNotasBinding
import com.example.appanotificaciones.model.Anotacion

class adapter_anotacion(
    var anotacionList: MutableList<Anotacion>,
    private val listener: OnClickListener): RecyclerView.Adapter<adapter_anotacion.ViewHolder>() {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val binding = ItemNotasBinding.bind(view)
        //eliminar interfas
        fun setListenner(anota: Anotacion)
        {
            binding.root.setOnClickListener{
                listener.onClick(anota)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_notas,parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = anotacionList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val anotacion = anotacionList.get(position)
        holder.setListenner(anotacion)
        holder.binding.tvTarea.text = anotacion.tarea
    }

    fun add(anota: Anotacion)
    {
        anotacionList.add(anota)
        notifyDataSetChanged()
    }

    fun remove(anota: Anotacion)
    {
        anotacionList.remove(anota)
        notifyDataSetChanged()
    }
}