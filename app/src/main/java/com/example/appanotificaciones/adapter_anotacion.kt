package com.example.appanotificaciones

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.example.appanotificaciones.databinding.ItemNotasBinding
import com.example.appanotificaciones.model.Anotacion

class adapter_anotacion(
    var anotacionList: MutableList<Anotacion>,
    private val listener: OnClickListener): RecyclerView.Adapter<adapter_anotacion.ViewHolder>() {

    private lateinit var context:Context

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val binding = ItemNotasBinding.bind(view)
        //eliminar interfas
        fun setListenner(anota: Anotacion)
        {
            binding.chkFinalizado.setOnClickListener { it
                anota.Finalizado = (it as CheckBox).isChecked
                listener.onChecked(anota)
            }
            binding.root.setOnClickListener{
                listener.onClick(anota, this@adapter_anotacion)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_notas,parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = anotacionList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val anotacion = anotacionList.get(position)
        holder.setListenner(anotacion)
        holder.binding.tvTarea.text = anotacion.tarea
        holder.binding.chkFinalizado.isChecked = anotacion.Finalizado

        if(anotacion.Finalizado){
            holder.binding.tvTarea.setTextSize(TypedValue.COMPLEX_UNIT_SP,
                context.resources.getDimension(R.dimen.tamtexto2))
        }
        else
        {
            holder.binding.tvTarea.setTextSize(TypedValue.COMPLEX_UNIT_SP,
                context.resources.getDimension(R.dimen.tamtexto1))
        }
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