package com.congnituscurso.cognitusproyect.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.congnituscurso.cognitusproyect.databinding.ItemEncuestaBinding
import com.congnituscurso.cognitusproyect.model.response.Encuesta

class EncuestaAdapter (private val items:List<Encuesta>):RecyclerView.Adapter<EncuestaAdapter.ViewHolder>(){
    private var contexto: Context? =null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EncuestaAdapter.ViewHolder {
        contexto=parent.context
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemEncuestaBinding.inflate(inflater,parent,false)
        return ViewHolder(binding)

    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }


    class ViewHolder(val binding: ItemEncuestaBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(encuestaModel: Encuesta) {
            binding.elemento= encuestaModel
            binding.executePendingBindings()
        }
    }
}