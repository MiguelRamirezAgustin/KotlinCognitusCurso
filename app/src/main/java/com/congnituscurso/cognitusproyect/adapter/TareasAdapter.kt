package com.congnituscurso.cognitusproyect.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.congnituscurso.cognitusproyect.R
import com.congnituscurso.cognitusproyect.activities.RealizarTareaActivity
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
        val tareaTitulo= items[position].tarea_titulo
        val tareaSubTitulo= items[position].tarea_subtit
        val tareaDescripcion= items[position].tarea_desc
        val tareaHoras= items[position].tarea_hrs
        val tareaI= items[position].usrtar_id
        val tareaF= items[position].usrtar_fin

        if(items[position].usrtar_status == "1"){
            holder.binding.linearGeneral.setBackgroundResource(R.drawable.group_18)
            holder.binding.tvTexto.setText("Normal")
            holder.binding.tvTexto.setTextColor(Color.parseColor("#27870A"))
        }else{
            holder.binding.linearGeneral.setBackgroundResource(R.drawable.group_17)
            holder.binding.tvTexto.setText("Urgente")
            holder.binding.tvTexto.setTextColor(Color.rgb(200,0,0))
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, RealizarTareaActivity::class.java)
            intent.putExtra("tareaT", tareaTitulo)
            intent.putExtra("tareaS", tareaSubTitulo)
            intent.putExtra("tareaD", tareaDescripcion)
            intent.putExtra("tareaH", tareaHoras)
            intent.putExtra("tareaI", tareaI)
            intent.putExtra("tareaF", tareaF)
            holder.itemView.context.startActivity(intent)
        }
    }


    class ViewHolder(val binding: ItemTareasBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(tareaModel: Tarea) {
            binding.elemento= tareaModel
            binding.executePendingBindings()
            binding.linearGeneral
        }
    }

}