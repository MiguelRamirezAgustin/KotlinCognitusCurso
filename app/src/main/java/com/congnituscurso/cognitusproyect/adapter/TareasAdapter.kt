package com.congnituscurso.cognitusproyect.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.congnituscurso.cognitusproyect.R
import com.congnituscurso.cognitusproyect.databinding.ItemTareasBinding
import com.congnituscurso.cognitusproyect.model.response.Tarea

class TareasAdapter(private val items: List<Tarea>) : RecyclerView.Adapter<TareasAdapter.ViewHolder>() {
    private var contexto: Context? =null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ViewHolder {
        contexto=parent.context
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTareasBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: TareasAdapter.ViewHolder, position: Int) {
        holder.bind(items[position])

        if(items[position].usrtar_status == "1"){
            holder.binding.linearGeneral.setBackgroundResource(R.drawable.group_18)
            holder.binding.tvTexto.setText("Normal")
        }else{
            holder.binding.linearGeneral.setBackgroundResource(R.drawable.group_17)
            holder.binding.tvTexto.setText("Urgente")
        }
    }


    class ViewHolder(val binding: ItemTareasBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tareaModel: Tarea) {
            val element = tareaModel
            binding.elemento= tareaModel
            binding.executePendingBindings()
            binding.linearGeneral
        }
    }

}